package com.roman.basearch.navigation

import com.roman.basearch.view.BaseActivity

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */

interface Command {
    fun execute(context: BaseActivity<*,*>)
}
