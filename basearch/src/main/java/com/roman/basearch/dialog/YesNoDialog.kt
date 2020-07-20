package com.roman.basearch.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.roman.basearch.R

/**
 *
 * Author: romanvysotsky
 * Created: 20.07.20
 */

class YesNoDialog : DialogFragment() {


    var yesNoDialogParams: YesNoDialogParams? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val yesNoDialogParams = yesNoDialogParams ?: placeHolderParams()

        return AlertDialog.Builder(requireContext(), android.R.style.ThemeOverlay_Material_Dialog_Alert)
            .setTitle(yesNoDialogParams.titleId)
            .setMessage(yesNoDialogParams.body ?: "")
            .setNegativeButton(yesNoDialogParams.noId) { _, _ ->
                yesNoDialogParams.noAction()
                dismiss()
            }
            .setPositiveButton(yesNoDialogParams.yesId) { _, _ ->
                yesNoDialogParams.yesAction()
            }
            .create()
    }

    override fun onStart() {
        super.onStart()

        if(yesNoDialogParams == null) {
            dismiss()
        }
    }

    private fun placeHolderParams(): YesNoDialogParams {

        return YesNoDialogParams(
            R.string.common_yes,
            R.string.common_no,
            R.string.app_name,
            null,
            { dismiss() }
        )
    }

}
