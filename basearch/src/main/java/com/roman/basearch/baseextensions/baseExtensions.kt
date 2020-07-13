package com.roman.basearch.baseextensions

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.roman.basearch.models.ActionMessage
import com.roman.basearch.view.BaseActivity
import com.roman.basearch.view.BaseFragment
import com.roman.basearch.viewmodel.BaseViewModel

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

fun <A: ViewDataBinding, B: BaseViewModel> BaseFragment<A, B>.closeKeyboardOnTouch(anyView: View) {

    anyView.setOnTouchListener { _, _ ->
        closeKeyboard()
        false
    }
}

fun <Binding: ViewDataBinding, ViewModel: BaseViewModel>
        BaseActivity<Binding, ViewModel>.showMessage(message: String) {

    if(message.isEmpty()) return

    val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)

    toast.setGravity(
        Gravity.CENTER or Gravity.BOTTOM,
        0,
        resources.getDimensionPixelSize(android.R.dimen.app_icon_size)
    )

    toast.show()
}

fun <Binding: ViewDataBinding, ViewModel: BaseViewModel>
        BaseActivity<Binding, ViewModel>.showActionMessage(action: ActionMessage) {

    val view = this.dataBinding.root
    val snackBar = Snackbar.make(view, action.message, Snackbar.LENGTH_LONG)
    snackBar.setAction(action.actionMessage) { action.action() }
    snackBar.show()
}

fun Context.getCurrentFragment(): Fragment? {
    val activity = this

    if(activity !is BaseActivity<*,*>) {
        return null
    }

    return activity.supportFragmentManager.getCurrentNavigationFragment()
}

fun FragmentManager.getCurrentNavigationFragment(): Fragment? =
    primaryNavigationFragment?.childFragmentManager?.fragments?.first()
