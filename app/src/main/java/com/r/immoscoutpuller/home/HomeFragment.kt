package com.r.immoscoutpuller.home

import androidx.fragment.app.viewModels
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.databinding.FragmentHomeBinding
import com.roman.basearch.view.BaseFragment

/**
 *
 * Author: romanvysotsky
 * Created: 05.07.23
 */

class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.fragment_home

}
