package com.r.immoscoutpuller.screens.settings

import com.r.immoscoutpuller.R
import com.roman.basearch.dialog.YesNoDialogCommand
import com.roman.basearch.dialog.YesNoDialogParams
import com.roman.basearch.navigation.Navigation
import com.roman.basearch.navigation.NavigationCommand
import com.roman.basearch.utility.TextLocalization
import com.roman.basearch.viewmodel.getKoinInstance

/**
 *
 * Author: romanvysotsky
 * Created: 20.07.20
 */

sealed class SettingsNavigation(override val command: NavigationCommand) : Navigation {

    class ToConfirmDelete(yesAction: () -> Unit) : SettingsNavigation(
        NavigationCommand.ViaCommand(
            YesNoDialogCommand(
                YesNoDialogParams(
                    R.string.settings_delete_old_yes,
                    R.string.settings_delete_old_no,
                    R.string.settings_delete_old,
                    getKoinInstance<TextLocalization>().getString(R.string.settings_delete_old_explanation),
                    yesAction
                )
            )
        )
    )

}