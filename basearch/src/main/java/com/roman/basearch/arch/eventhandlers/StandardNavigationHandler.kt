package com.roman.basearch.arch.eventhandlers

import androidx.lifecycle.Observer
import com.roman.basearch.navigation.NavigationCommand
import com.roman.basearch.repository.AnalyticsRepository
import com.roman.basearch.view.BaseActivity
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */

class StandardNavigationHandler(private val context: BaseActivity<*, *>)
    : Observer<NavigationCommand>, KoinComponent {

    private val analytics: AnalyticsRepository by inject()


    override fun onChanged(command: NavigationCommand) {

        try {
            navigate(command)
        } catch (e: IllegalArgumentException){
            logNavigationException(e)
        }
    }

    private fun navigate(command: NavigationCommand) {

        val controller = context.getNavHostController()

        when(command) {

            is NavigationCommand.BackTo
            -> finishWhenNoItemsOnBackStack(
                controller.popBackStack(command.destinationId, false)
            )

            is NavigationCommand.Back
            -> finishWhenNoItemsOnBackStack(controller.popBackStack())

            is NavigationCommand.ViaDirection
            -> controller.navigate(command.direction)

            is NavigationCommand.ViaCommand
            -> { command.command.execute(context) }
        }
    }


    private fun logNavigationException(e: IllegalArgumentException) {
        analytics.logException(e)
    }

    private fun finishWhenNoItemsOnBackStack(wasPopped: Boolean) {

        if(!wasPopped) {
            context.finish()
        }
    }

}
