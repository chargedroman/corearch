package com.roman.basearch.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.roman.basearch.arch.AutoClearedValue
import com.roman.basearch.models.Permission
import com.roman.basearch.viewmodel.BaseViewModel

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */

abstract class BaseFragment<Binding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment() {

    protected var dataBinding by AutoClearedValue<Binding>()

    protected abstract val viewModel: ViewModel
    protected abstract val layoutResourceId: Int

    private var baseActivity: BaseActivity<*,*>? = null


    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =

        DataBindingUtil.inflate<Binding>(
            inflater, layoutResourceId, container, false
        ).also {
            dataBinding = it
        }.root


    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = context as BaseActivity<*,*>
    }

    override fun onDetach() {
        super.onDetach()
        baseActivity = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.setVariable(BR.viewModel, viewModel)
        observeBaseEvents()
    }

    fun updateToolbarTitle(title: String) {
        baseActivity?.supportActionBar?.title = title
    }

    fun askUserForPermission(permission: Permission, onPermissionChanged: (Boolean) -> Unit) {

        val activity = context

        if (activity is BaseActivity<*,*>) {
            activity.requestPermission(permission, object : BaseActivity.Granted {
                override fun wasGranted(wasGranted: Boolean) {
                    onPermissionChanged(wasGranted)
                }
            })
        }
    }

    fun closeKeyboard() {
        baseActivity?.closeKeyboard()
    }

    private fun observeBaseEvents() {

        viewModel.message.observe(viewLifecycleOwner, Observer {
            baseActivity?.messageHandler?.onChanged(it)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            baseActivity?.errorHandler?.onChanged(it)
        })

        viewModel.actionMessage.observe(viewLifecycleOwner, Observer {
            baseActivity?.actionMessageHandler?.onChanged(it)
        })

        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            baseActivity?.navigationHandler?.onChanged(it.command)
        })

        viewModel.closeKeyBoard.observe(viewLifecycleOwner, Observer {
            baseActivity?.closeKeyboard()
        })
    }

}
