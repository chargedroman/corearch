package com.r.immoscoutpuller.screens.basepull

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.databinding.FragmentPullBinding
import com.r.immoscoutpuller.model.ImmoItem
import com.r.immoscoutpuller.model.PullAdapter
import com.roman.basearch.arch.AutoClearedValue
import com.roman.basearch.baseextensions.closeKeyboardOnTouch
import com.roman.basearch.view.BaseFragment

/**
 *
 * Author: romanvysotsky
 * Created:
 */

abstract class PullFragment<Type: ImmoItem> : BaseFragment<FragmentPullBinding, PullViewModel<Type>>() {

    private var adapter by AutoClearedValue<PullAdapter<Type>>()

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

        adapter = PullAdapter<Type> {
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
