package com.gsc.app.utils.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.gsc.app.R
import com.gsc.app.databinding.LayoutActionBarBinding

class GscActionBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutActionBarBinding
    val title get() = this@GscActionBar.binding.tvTitle
    val backButton get() = this@GscActionBar.binding.ivBack

    init {
        binding = LayoutActionBarBinding.inflate(LayoutInflater.from(context), this, true)
        context.theme.obtainStyledAttributes(attrs, R.styleable.GscActionBar, 0, 0).apply {
            try {
                val title = getString(R.styleable.GscActionBar_title)
                binding.tvTitle.text = title
            } finally {
                recycle()
            }
        }
    }
}