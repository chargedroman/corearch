package com.roman.basearch.arch.eventhandlers

import androidx.lifecycle.Observer
import com.roman.basearch.baseextensions.showMessage
import com.roman.basearch.view.BaseActivity

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

class StandardMessageHandler(private val context: BaseActivity<*, *>) : Observer<String> {

    override fun onChanged(message: String?) {

        if(message != null) {
            context.showMessage(message)
        }
    }

}
