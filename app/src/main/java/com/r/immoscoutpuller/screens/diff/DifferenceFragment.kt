package com.r.immoscoutpuller.screens.diff

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.databinding.FragmentDifferenceBinding
import com.roman.basearch.arch.AutoClearedValue
import com.roman.basearch.baseextensions.closeKeyboardOnTouch
import com.roman.basearch.view.BaseFragment

/**
 *
 * Author: romanvysotsky
 * Created: 17.07.20
 */

class DifferenceFragment : BaseFragment<FragmentDifferenceBinding, DifferenceViewModel>() {

    private var adapter by AutoClearedValue<DifferenceAdapter>()

    private val lazy: DifferenceViewModel by viewModels()

    override val viewModel: DifferenceViewModel get() = lazy
    override val layoutResourceId: Int get() = R.layout.fragment_difference


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

        adapter = DifferenceAdapter {
            viewModel.onDiffItemClicked(it)
        }

        closeKeyboardOnTouch(recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {

        viewModel.diffItems.observe(viewLifecycleOwner, Observer {
            if(it != null) adapter.submitList(it)
        })
    }

}
