package com.roman.basearch.navigation

import androidx.navigation.NavDirections

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */

sealed class NavigationCommand {

    object Back: NavigationCommand()
    data class BackTo(val destinationId: Int): NavigationCommand()

    data class ViaDirection(val direction: NavDirections): NavigationCommand()
    data class ViaCommand(val command: Command): NavigationCommand()

}
