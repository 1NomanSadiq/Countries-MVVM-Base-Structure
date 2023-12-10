package nom.mvvm.structure.utils.extensions.views

import android.widget.ImageView
import com.bumptech.glide.Glide
import nom.mvvm.structure.R

fun ImageView.load(image: String, default: Int = R.color.black, centerCrop: Boolean = true) {
    if (centerCrop) {
        Glide.with(context)
            .load(image)
            .placeholder(default)
            .centerCrop()
            .into(this)
    } else {
        Glide.with(context)
            .load(image)
            .placeholder(default)
            .fitCenter()
            .into(this)
    }
}
