package com.roman.basearch.arch.eventhandlers

import androidx.lifecycle.Observer
import com.roman.basearch.baseextensions.showActionMessage
import com.roman.basearch.models.ActionMessage
import com.roman.basearch.view.BaseActivity

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

class StandardActionMessageHandler(private val context: BaseActivity<*,*>) : Observer<ActionMessage> {

    override fun onChanged(actionMessage: ActionMessage?) {

        if(actionMessage != null) {
            context.showActionMessage(actionMessage)
        }
    }

}
