package nom.mvvm.structure.ui.splash.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.textfield.TextInputEditText

class CustomEditText : TextInputEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun getLayoutDirection(): Int {
        return View.LAYOUT_DIRECTION_LTR
    }
}
