package com.r.immoscoutpuller.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.databinding.FragmentSettingsBinding
import com.roman.basearch.view.BaseFragment
import com.roman.basearch.view.list.RecyclerViewBuilder

/**
 *
 * Author: romanvysotsky
 * Created: 05.07.23
 */

class SettingsFragment: BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    override val viewModel: SettingsViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.fragment_settings


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ListItemAdapter { viewModel.onListItemClicked(it) }
        val refresh = RecyclerViewBuilder.PullToRefresh(dataBinding.layoutSwiper, viewModel.list) {
            viewModel.refreshItems()
        }

        RecyclerViewBuilder(dataBinding.layoutRecycler)
            .addAdapter(adapter, viewModel.list)
            .setPullToRefresh(refresh)
            .addItemDividers()
            .build(this)
    }

}
