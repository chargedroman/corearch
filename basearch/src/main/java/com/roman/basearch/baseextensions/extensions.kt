package com.roman.basearch.baseextensions

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.roman.basearch.arch.adapters.AdapterOnSpinnerItemSelected
import java.math.BigDecimal
import java.math.RoundingMode

/**
 *
 * Author: romanvysotsky
 * Created: 26.08.23
 */


/**
 *  shows items with one simple text from toString()
 */
fun <T> Fragment.setupSimpleItemSpinner(
    spinner: Spinner,
    position: LiveData<Int>,
    liveData: LiveData<List<T>>,
    onItemChanged: (T, Int) -> Unit
) = liveData.observe(viewLifecycleOwner) { items ->
    if(items == null) return@observe

    val adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, items)
    val listener = AdapterOnSpinnerItemSelected(onItemChanged)

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapter

    var notifiedFirstItem = false

    position.observe(viewLifecycleOwner) {
        if (it != null) {
            if (!notifiedFirstItem) {
                notifiedFirstItem = true
                spinner.onItemSelectedListener = listener
                spinner.setSelection(it, false)
            } else {
                spinner.onItemSelectedListener = null
                spinner.setSelection(it, false)
                spinner.onItemSelectedListener = listener
            }
        }
    }
}

fun Double.round(scale: Int): Double {
    val decimal = BigDecimal(this)
    val roundOff = decimal.setScale(scale, RoundingMode.HALF_UP)
    return roundOff.toDouble()
}
