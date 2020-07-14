package com.r.immoscoutpuller

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.r.immoscoutpuller.databinding.ActivityMainBinding
import com.roman.basearch.view.BaseActivity


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    val lazy: MainViewModel by viewModels()

    override val viewModel: MainViewModel get() = lazy
    override val layoutResourceId: Int get() = R.layout.activity_main


    override fun getNavHostController(): NavController {
        return findNavController(R.id.nav_host_fragment_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.homeNavView.setupWithNavController(getNavHostController())
    }

}
