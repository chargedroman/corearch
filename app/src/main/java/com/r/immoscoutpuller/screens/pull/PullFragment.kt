package com.r.immoscoutpuller.screens.pull

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.databinding.FragmentPullBinding
import com.roman.basearch.arch.AutoClearedValue
import com.roman.basearch.baseextensions.closeKeyboardOnTouch
import com.roman.basearch.view.BaseFragment

/**
 *
 * Author: romanvysotsky
 * Created:
 */

class PullFragment : BaseFragment<FragmentPullBinding, PullViewModel>() {

    private var adapter by AutoClearedValue<PullAdapter>()

    private val lazy: PullViewModel by viewModels()

    override val viewModel: PullViewModel get() = lazy
    override val layoutResourceId: Int get() = R.layout.fragment_pull


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRefresh()
        setupRecyclerView()
        observeViewModel()
    }


    private fun setupRefresh() {

        dataBinding.layoutRefresh.setOnRefreshListener {
            viewModel.onUserRefreshedData()
        }

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if(it == false) dataBinding.layoutRefresh.isRefreshing = false
        })
    }

    private fun setupRecyclerView() {
        val recyclerView = dataBinding.recyclerView
        val orientation = RecyclerView.VERTICAL
        val layoutManager = LinearLayoutManager(context, orientation, false)
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, layoutManager.orientation)

        adapter = PullAdapter {
            viewModel.onImmoItemClicked(it)
        }

        closeKeyboardOnTouch(recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {

        viewModel.immoItems.observe(viewLifecycleOwner, Observer {
            if(it != null) adapter.submitList(it)
        })
    }

}
