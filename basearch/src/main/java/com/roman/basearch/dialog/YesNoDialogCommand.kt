package com.roman.basearch.dialog

import com.roman.basearch.navigation.Command
import com.roman.basearch.view.BaseActivity

/**
 *
 * Author: romanvysotsky
 * Created: 20.07.20
 */

class YesNoDialogCommand(private val params: YesNoDialogParams) :
    Command {

    override fun execute(context: BaseActivity<*,*>) {
        val frag = YesNoDialog()
        frag.yesNoDialogParams = params
        frag.show(context.supportFragmentManager, YesNoDialog::class.java.name)
    }

}
