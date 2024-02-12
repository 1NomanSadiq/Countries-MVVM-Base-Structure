package com.gsc.app.utils.misc

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import android.view.animation.LinearInterpolator

class RefreshIconView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }
    private val path = Path()
    private var rotation = 0f
    private val animator = ValueAnimator.ofFloat(0f, 360f).apply {
        duration = 1000
        repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()
        addUpdateListener {
            rotation = it.animatedValue as Float
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        path.reset()
        path.moveTo(width * 0.3f, height * 0.5f)
        path.lineTo(width * 0.5f, height * 0.7f)
        path.lineTo(width * 0.7f, height * 0.5f)
        canvas.save()
        canvas.rotate(rotation, width / 2, height / 2)
        canvas.drawPath(path, paint)
        canvas.restore()
    }

    fun startAnimation() {
        animator.start()
    }

    fun stopAnimation() {
        animator.cancel()
    }
}