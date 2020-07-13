package com.r.immoscoutpuller.pull

import android.view.View
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.databinding.FragmentPullBinding
import com.roman.basearch.view.BaseFragment

/**
 *
 * Author: romanvysotsky
 * Created:
 */

class PullFragment : BaseFragment<FragmentPullBinding, PullViewModel>() {

    private val lazy: PullViewModel by viewModels()

    override val viewModel: PullViewModel get() = lazy
    override val layoutResourceId: Int get() = R.layout.fragment_pull


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO setup stuff like recyclerviews, observing viewmodel ...
    }

}
