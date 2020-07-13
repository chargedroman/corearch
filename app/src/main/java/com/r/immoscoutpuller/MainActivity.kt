package com.r.immoscoutpuller

import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.roman.basearch.databinding.ActivityMainBinding
import com.roman.basearch.view.BaseActivity


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    val lazy: MainViewModel by viewModels()

    override val viewModel: MainViewModel get() = lazy
    override val layoutResourceId: Int get() = R.layout.activity_main


    override fun getNavHostController(): NavController {
        return findNavController(R.id.nav_host_fragment_main)
    }

}
