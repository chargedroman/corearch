package com.r.immoscoutpuller.screens.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.databinding.FragmentSettingsBinding
import com.roman.basearch.view.BaseFragment

/**
 *
 * Author: romanvysotsky
 * Created:
 */

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    private val lazy: SettingsViewModel by viewModels()

    override val viewModel: SettingsViewModel get() = lazy
    override val layoutResourceId: Int get() = R.layout.fragment_settings


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}
