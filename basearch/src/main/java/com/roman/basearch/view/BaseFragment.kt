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
import com.roman.basearch.arch.AutoClearedValue
import com.roman.basearch.models.Permission
import com.roman.basearch.repository.PermissionRepository
import com.roman.basearch.viewmodel.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */

abstract class BaseFragment<Binding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment(), KoinComponent {

    protected var dataBinding by AutoClearedValue<Binding>()

    protected abstract val viewModel: ViewModel
    protected abstract val layoutResourceId: Int

    private val permissionRepository: PermissionRepository by inject()

    private var baseActivity: BaseActivity<*,*>? = null


    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =

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

    private fun askUserForPermission(permission: Permission, onPermissionChanged: (Boolean) -> Unit) {

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

        permissionRepository.observe().observe(viewLifecycleOwner) {
            askUserForPermission(it.first, it.second)
        }

        viewModel.message.observe(viewLifecycleOwner) {
            baseActivity?.messageHandler?.onChanged(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            baseActivity?.errorHandler?.onChanged(it)
        }

        viewModel.actionMessage.observe(viewLifecycleOwner) {
            baseActivity?.actionMessageHandler?.onChanged(it)
        }

        viewModel.navigation.observe(viewLifecycleOwner) {
            baseActivity?.navigationHandler?.onChanged(it.command)
        }

        viewModel.closeKeyBoard.observe(viewLifecycleOwner) {
            baseActivity?.closeKeyboard()
        }
    }

}
