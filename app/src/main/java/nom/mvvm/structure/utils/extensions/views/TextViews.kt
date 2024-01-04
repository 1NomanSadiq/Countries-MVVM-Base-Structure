package nom.mvvm.structure.utils.extensions.views

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import nom.mvvm.structure.R

fun TextView.textWatcher(init: KTextWatcher.() -> Unit) {
    addTextChangedListener(KTextWatcher().apply(init))
}

fun TextView.setTextOrGone(value: String?) {
    if (value.isNullOrEmpty())
        isVisible = false
    else text = value
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

fun EditText.addUniqueTextChangedListener(onTextChanged: (text: CharSequence?) -> Unit) {
    removeTextChangedListener(this.onTextChangedListener)
    val listener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(s)
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    onTextChangedListener = listener
    addTextChangedListener(listener)
}

private var EditText.onTextChangedListener: TextWatcher?
    get() = getTag(R.string.textChangeListener) as? TextWatcher
    set(value) = setTag(R.string.textChangeListener, value)