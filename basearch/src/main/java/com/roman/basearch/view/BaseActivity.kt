package com.roman.basearch.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.roman.basearch.BR
import com.roman.basearch.arch.eventhandlers.StandardActionMessageHandler
import com.roman.basearch.arch.eventhandlers.StandardErrorHandler
import com.roman.basearch.arch.eventhandlers.StandardMessageHandler
import com.roman.basearch.arch.eventhandlers.StandardNavigationHandler
import com.roman.basearch.models.Permission
import com.roman.basearch.viewmodel.BaseViewModel

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

abstract class BaseActivity<Binding : ViewDataBinding, ViewModel : BaseViewModel> : AppCompatActivity() {

    lateinit var dataBinding: Binding
    lateinit var errorHandler: StandardErrorHandler
    lateinit var messageHandler: StandardMessageHandler
    lateinit var actionMessageHandler: StandardActionMessageHandler
    lateinit var navigationHandler: StandardNavigationHandler

    protected abstract val viewModel: ViewModel
    protected abstract val layoutResourceId: Int

    private val permissionCallbacks: MutableMap<Int, Granted> = mutableMapOf()
    private val forResultCallbacks: MutableMap<Int, (data: Intent?) -> Unit> = mutableMapOf()

    abstract fun getNavHostController(): NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, layoutResourceId)
        dataBinding.lifecycleOwner = this
        dataBinding.setVariable(BR.viewModel, viewModel)

        messageHandler =
            StandardMessageHandler(this)
        errorHandler =
            StandardErrorHandler(this)
        actionMessageHandler =
            StandardActionMessageHandler(this)
        navigationHandler =
            StandardNavigationHandler(this)

        observeBaseEvents()
    }

    override fun onStop() {
        super.onStop()
        Glide.get(this).clearMemory()
    }


    fun closeKeyboard() {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        currentFocus?.clearFocus()
    }

    fun startActivityForResultWithCallback(intent: Intent, code: Int, onSuccess: (data: Intent?) -> Unit) {
        forResultCallbacks[code] = onSuccess
        startActivityForResult(intent, code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val callback = forResultCallbacks[requestCode]
        forResultCallbacks.remove(requestCode)

        if(callback != null && resultCode == Activity.RESULT_OK) {
            callback(data)
        }
    }

    open fun requestPermission(permission: Permission, callback: Granted) {
        permissionCallbacks[permission.code] = callback

        ActivityCompat.requestPermissions(
            this,
            permission.keys,
            permission.code
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val callback = permissionCallbacks.remove(requestCode) ?: return

        val wasGranted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED

        callback.wasGranted(wasGranted)
    }

    private fun observeBaseEvents() {

        viewModel.error.observe(this, errorHandler)
        viewModel.message.observe(this, messageHandler)
        viewModel.actionMessage.observe(this, actionMessageHandler)
        viewModel.closeKeyBoard.observe(this, Observer { closeKeyboard() })

        viewModel.navigation.observe(this, Observer {
            navigationHandler.onChanged(it.command)
        })
    }


    interface Granted {
        fun wasGranted(wasGranted: Boolean)
    }
}
