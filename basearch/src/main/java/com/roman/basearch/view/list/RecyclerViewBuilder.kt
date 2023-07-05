package com.roman.basearch.view.list

import androidx.annotation.DimenRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.roman.basearch.R
import com.roman.basearch.arch.adapters.AdapterOnScrolling
import com.roman.basearch.arch.adapters.AdapterSwipeToDelete
import com.roman.basearch.baseextensions.closeKeyboardOnTouch
import com.roman.basearch.view.BaseFragment
import com.roman.basearch.view.list.decoration.MarginDecoration

/**
 * Used to shorten setup of RecyclerViews
 * Simplest use then would be RecyclerViewBuilder(recyclerView).addAdapter(adapter, liveData).build(viewLifeCycleOwner)
 * This creates a vertical linear list and handles livedata observe + submit
 *
 * Author: romanvysotsky
 * Created: 18.05.22
 */

class RecyclerViewBuilder(
    val recyclerView: FragmentRecyclerView,
    val orientation: Int = RecyclerView.VERTICAL,
) {


    private val addedAdapters: MutableList<AdapterAddable> = mutableListOf()
    private val addedDecorations: MutableList<RecyclerView.ItemDecoration> = mutableListOf()
    private var addedLayoutManager: RecyclerView.LayoutManager? = null
    private var addedSwipeToDelete: SwipeToDelete? = null
    private var addedPullToRefresh: PullToRefresh<*>? = null



    fun <Item, Adapter: ListAdapter<Item, *>> addAdapter(
        listAdapter: Adapter,
        source: LiveData<List<Item>>,
        onUpdate: (Adapter, List<Item>) -> Unit = { adapter, list -> adapter.submitList(list) },
    ): RecyclerViewBuilder {
        val save = AddedAdapterDynamicSource(listAdapter, source, onUpdate)
        addedAdapters.add(save)
        return this
    }

    fun <Item, Adapter: ListAdapter<Item, *>> addAdapter(
        listAdapter: Adapter,
        source: List<Item>,
        onUpdate: (Adapter, List<Item>) -> Unit = { adapter, list -> adapter.submitList(list) },
    ): RecyclerViewBuilder {
        val save = AddedAdapterStaticSource(listAdapter, source, onUpdate)
        addedAdapters.add(save)
        return this
    }


    fun <Item> addAdapter(
        listAdapter: BaseHeaderFooterAdapter<*>,
        source: LiveData<List<Item>>
    ): RecyclerViewBuilder {
        val save = AddedAdapterHeaderFooter(listAdapter, source)
        addedAdapters.add(save)
        return this
    }

    fun <Item> addAdapter(
        listAdapter: BaseHeaderFooterAdapter<*>,
        source: LiveData<Item>,
        canShowContent: (Item) -> Boolean
    ): RecyclerViewBuilder {
        val save = AddedAdapterHeaderFooterCustom(listAdapter, source, canShowContent)
        addedAdapters.add(save)
        return this
    }


    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager): RecyclerViewBuilder {
        this.addedLayoutManager = layoutManager
        return this
    }

    fun setSwipeToDelete(swipeToDelete: SwipeToDelete): RecyclerViewBuilder {
        this.addedSwipeToDelete = swipeToDelete
        return this
    }

    fun setPullToRefresh(pullToRefresh: PullToRefresh<*>): RecyclerViewBuilder {
        this.addedPullToRefresh = pullToRefresh
        return this
    }


    fun addItemDecoration(decoration: RecyclerView.ItemDecoration): RecyclerViewBuilder {
        addedDecorations.add(decoration)
        return this
    }

    fun addItemDividers(): RecyclerViewBuilder {
        val decoration = DividerItemDecoration(recyclerView.context, orientation)
        return addItemDecoration(decoration)
    }

    fun addItemMargin(@DimenRes marginResource: Int = R.dimen.marginStandard): RecyclerViewBuilder {
        val margin = recyclerView.context.resources.getDimensionPixelSize(marginResource)
        val marginDecoration = MarginDecoration(
            verticalMargin = margin,
            horizontalMargin = margin,
            layoutOrientation = orientation
        )
        return addItemDecoration(marginDecoration)
    }

    fun addOnScrolledListener(onScrolled: () -> Unit): RecyclerViewBuilder {
        recyclerView.addOnScrollListener(AdapterOnScrolling(onScrolled))
        return this
    }


    fun build(fragment: BaseFragment<*, *>) {
        fragment.closeKeyboardOnTouch(recyclerView)
        build(fragment.viewLifecycleOwner)
    }

    fun build(viewLifecycleOwner: LifecycleOwner) {
        val adapter = getAdapter()
        recyclerView.setLayoutManager()
        recyclerView.setItemDecorations()
        recyclerView.setNullableAdapter(adapter)

        addedSwipeToDelete?.build(recyclerView, viewLifecycleOwner)
        addedPullToRefresh?.observe(viewLifecycleOwner)
        for(addedAdapter in addedAdapters) {
            addedAdapter.observe(viewLifecycleOwner)
        }
    }



    private fun getAdapter(): RecyclerView.Adapter<*> {
        return when(addedAdapters.size) {
            0 -> throw IllegalArgumentException("Builder does not allow 0 adapters.")
            1 -> addedAdapters.first().getAdapter()
            else -> getConcatAdapter()
        }
    }

    private fun getConcatAdapter(): RecyclerView.Adapter<*> {
        val listOfAdapters = addedAdapters.map { it.getAdapter() }
        return ConcatAdapter(listOfAdapters)
    }

    private fun RecyclerView.setLayoutManager() {
        val layoutManager = addedLayoutManager ?: LinearLayoutManager(context, orientation, false)
        this.layoutManager = layoutManager
    }

    private fun RecyclerView.setItemDecorations() {
        for(decoration in addedDecorations) {
            addItemDecoration(decoration)
        }
    }



    /**
     * Pull to refresh, calls [actionOnRefresh] when user pulls to refesh
     * Hides refresh animation when [refreshOnUpdate] fires next event
     */
    class PullToRefresh<Item>(
        private val refreshLayout: SwipeRefreshLayout,
        private val refreshOnUpdate: LiveData<Item>,
        private val actionOnRefresh: () -> Unit,
    ) {

        fun observe(viewLifecycleOwner: LifecycleOwner) {
            refreshLayout.setOnRefreshListener { actionOnRefresh.invoke() }
            refreshOnUpdate.observe(viewLifecycleOwner) {
                refreshLayout.isRefreshing = false
            }
        }

    }


    class SwipeToDelete(
        private val itemChanged: LiveData<Int>,
        private val onSwiped: (RecyclerView.ViewHolder) -> Unit,
        private val canSwipeOn: (RecyclerView.ViewHolder) -> Boolean = { true },
    ) {

        fun build(recyclerView: FragmentRecyclerView, viewLifecycleOwner: LifecycleOwner) {
            val swipeDelete = AdapterSwipeToDelete(
                recyclerView.context, android.R.color.holo_red_dark, R.drawable.ic_trash, onSwiped
            )
            swipeDelete.canSwipeOn = canSwipeOn
            val touchHelper = ItemTouchHelper(swipeDelete)
            touchHelper.attachToRecyclerView(recyclerView)

            itemChanged.observe(viewLifecycleOwner) {
                val adapter = recyclerView.adapter
                val itemCount = adapter?.itemCount ?: 0
                if (it != null && itemCount > it && it >= 0) adapter?.notifyItemChanged(it)
            }
        }

    }


    /**
     * internal helper classes below
     */

    private interface AdapterAddable {
        fun getAdapter(): RecyclerView.Adapter<*>
        fun observe(viewLifecycleOwner: LifecycleOwner)
    }

    private class AddedAdapterDynamicSource<Item, Adapter: ListAdapter<Item, *>>(
        val listAdapter: Adapter,
        val source: LiveData<List<Item>>,
        val onUpdate: (Adapter, List<Item>) -> Unit = { adapter, list -> adapter.submitList(list) },
    ): AdapterAddable {

        override fun getAdapter(): RecyclerView.Adapter<*> = listAdapter

        override fun observe(viewLifecycleOwner: LifecycleOwner) {
            source.observe(viewLifecycleOwner) {
                if(it != null) onUpdate(listAdapter, it)
            }
        }

    }

    private class AddedAdapterStaticSource<Item, Adapter: ListAdapter<Item, *>>(
        val listAdapter: Adapter,
        val source: List<Item>,
        val onUpdate: (Adapter, List<Item>) -> Unit = { adapter, list -> adapter.submitList(list) },
    ): AdapterAddable {

        override fun getAdapter(): RecyclerView.Adapter<*> = listAdapter

        override fun observe(viewLifecycleOwner: LifecycleOwner) {
            onUpdate(listAdapter, source)
        }

    }


    private class AddedAdapterHeaderFooter<Item>(
        val listAdapter: BaseHeaderFooterAdapter<*>,
        val source: LiveData<List<Item>>,
    ): AdapterAddable {

        override fun getAdapter(): RecyclerView.Adapter<*> = listAdapter

        override fun observe(viewLifecycleOwner: LifecycleOwner) {
            source.observe(viewLifecycleOwner) {
                if(it != null) listAdapter.onDataUpdated(it)
            }
        }

    }

    private class AddedAdapterHeaderFooterCustom<Item>(
        val listAdapter: BaseHeaderFooterAdapter<*>,
        val source: LiveData<Item>,
        val canShowContent: (Item) -> Boolean
    ): AdapterAddable {

        override fun getAdapter(): RecyclerView.Adapter<*> = listAdapter

        override fun observe(viewLifecycleOwner: LifecycleOwner) {
            source.observe(viewLifecycleOwner) {
                if(it != null) {
                    if(canShowContent(it))
                        listAdapter.showContent()
                    else
                        listAdapter.hideContent()
                }
            }
        }

    }


}
