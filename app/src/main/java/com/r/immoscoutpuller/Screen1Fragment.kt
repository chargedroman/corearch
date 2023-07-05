package com.r.immoscoutpuller

import androidx.fragment.app.viewModels
import com.r.immoscoutpuller.databinding.Fragment1Binding
import com.roman.basearch.view.BaseFragment

/**
 *
 * Author: romanvysotsky
 * Created: 05.07.23
 */

class Screen1Fragment: BaseFragment<Fragment1Binding, Screen1ViewModel>() {

    override val viewModel: Screen1ViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.fragment_1

}
