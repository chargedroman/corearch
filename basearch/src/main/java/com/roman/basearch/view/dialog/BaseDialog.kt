package com.roman.basearch.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.roman.basearch.BR
import com.roman.basearch.view.BaseActivity
import com.roman.basearch.viewmodel.BaseViewModel

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-12
 */

abstract class BaseDialog<Binding : ViewDataBinding, ViewModel : BaseViewModel>: DialogFragment() {

    protected var dataBinding: Binding? = null
    protected abstract val viewModel: ViewModel
    protected abstract val layoutResourceId: Int

    private var baseActivity: BaseActivity<*,*>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = createBinding()
        dataBinding = binding

        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.viewModel, viewModel)

        observeBaseEvents()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is BaseActivity<*,*>) {
            baseActivity = context
        }
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBinding = null
    }

    override fun onDetach() {
        super.onDetach()
        baseActivity = null
    }

    private fun createBinding(): Binding {

        return DataBindingUtil.inflate(
            requireActivity().layoutInflater,
            layoutResourceId,
            null,
            false
        )
    }

    private fun observeBaseEvents() {

        viewModel.message.observe(viewLifecycleOwner, Observer {
            baseActivity?.messageHandler?.onChanged(it)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            baseActivity?.errorHandler?.onChanged(it)
        })

        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            baseActivity?.navigationHandler?.onChanged(it.command)
        })

        viewModel.closeKeyBoard.observe(viewLifecycleOwner, Observer {
            baseActivity?.closeKeyboard()
        })
    }

}
