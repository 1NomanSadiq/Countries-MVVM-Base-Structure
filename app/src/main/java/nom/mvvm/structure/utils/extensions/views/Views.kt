package nom.mvvm.structure.utils.extensions.views

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible

fun <T : View> T.afterMeasured(f: T.() -> Unit) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            f()
            return false
        }
    })
}

fun View.removeFromParent() {
    if (parent is ViewGroup) {
        (parent as ViewGroup).removeView(this)
    }
}

fun View.disableTouch() {
    setOnTouchListener { _, _ -> true }
}

fun View.setVisible(isVisible: Boolean, animate: Boolean = false) {
    if (animate) {
        if (isVisible) {
            alpha = 0f
            setVisible(true)
        }
        ViewCompat.animate(this)
            .alpha(if (isVisible) 1f else 0f)
            .withEndAction {
                if (!isVisible) {
                    setVisible(false)
                }
            }
    } else {
        visibility = if (isVisible) VISIBLE else GONE
    }
}


fun View.show() {
    isVisible = true
}

fun View.hide() {
    isVisible = false
}