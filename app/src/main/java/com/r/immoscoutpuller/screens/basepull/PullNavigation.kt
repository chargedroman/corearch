package com.r.immoscoutpuller.screens.basepull

import com.r.immoscoutpuller.command.DeepLinkCommand
import com.roman.basearch.navigation.Navigation
import com.roman.basearch.navigation.NavigationCommand

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

sealed class PullNavigation(override val command: NavigationCommand) : Navigation {

    class ToWeb(link: String, onError: (Throwable) -> Unit): PullNavigation(
        NavigationCommand.ViaCommand(
            DeepLinkCommand(link, onError)
        )
    )

}
