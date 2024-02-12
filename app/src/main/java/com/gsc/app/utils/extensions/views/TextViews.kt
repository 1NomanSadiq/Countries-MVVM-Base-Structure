package com.gsc.app.utils.extensions.views

import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.core.view.isVisible
import com.gsc.app.R
import com.gsc.app.utils.misc.NoFilterArrayAdapter

fun <T> AutoCompleteTextView.attach(items: List<T>, filter: Boolean = false) {
    val adapter =
        if (filter) ArrayAdapter(context, R.layout.dropdown_item, items) else NoFilterArrayAdapter(
            context,
            R.layout.dropdown_item,
            items
        )
    setAdapter(adapter)
}

fun TextView.textWatcher(init: KTextWatcher.() -> Unit) {
    addTextChangedListener(KTextWatcher().apply(init))
}

fun TextView.setTextOrGone(value: String?) {
    if (value.isNullOrEmpty())
        isVisible = false
    else {
        text = value
        isVisible = true
    }
}

class KTextWatcher : TextWatcher {

    private var _beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
    private var _onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
    private var _afterTextChanged: ((Editable?) -> Unit)? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        _beforeTextChanged?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        _onTextChanged?.invoke(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable?) {
        _afterTextChanged?.invoke(s)
    }

    fun beforeTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
        _beforeTextChanged = listener
    }

    fun onTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
        _onTextChanged = listener
    }

    fun afterTextChanged(listener: (Editable?) -> Unit) {
        _afterTextChanged = listener
    }
}
