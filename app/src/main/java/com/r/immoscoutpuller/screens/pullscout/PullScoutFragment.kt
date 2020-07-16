package com.r.immoscoutpuller.screens.pullscout

import androidx.fragment.app.viewModels
import com.r.immoscoutpuller.model.PresentableImmoScoutItem
import com.r.immoscoutpuller.screens.basepull.PullFragment
import com.r.immoscoutpuller.screens.basepull.PullViewModel

/**
 *
 * Author: romanvysotsky
 * Created: 16.07.20
 */

class PullScoutFragment : PullFragment<PresentableImmoScoutItem>() {

    val lazy: PullScoutViewModel by viewModels()

    override val viewModel: PullViewModel<PresentableImmoScoutItem> get() = lazy

}
