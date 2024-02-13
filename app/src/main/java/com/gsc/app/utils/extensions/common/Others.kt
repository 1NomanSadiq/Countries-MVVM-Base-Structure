package com.gsc.app.utils.extensions.common

import android.app.Activity
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

val Any.TAG get() = this.javaClass.simpleName
fun Activity.compress(file: File?, onReady: (File) -> Unit) {
    showProgressDialog()
    val ctx = this
    (this as AppCompatActivity).lifecycleScope.launch(Dispatchers.Main) {
        val compressedImage = Compressor.compress(ctx, file!!) {
            resolution(1280, 1280)
            format(Bitmap.CompressFormat.JPEG)
            quality(100)
        }
        dismissProgressDialog()
        onReady(compressedImage)
    }
}

fun Fragment.compress(file: File?, onReady: (File) -> Unit) {
    showProgressDialog()
    val ctx = requireActivity()
    (ctx as AppCompatActivity).lifecycleScope.launch(Dispatchers.Main) {
        val compressedImage = Compressor.compress(ctx, file!!) {
            resolution(1280, 1280)
            format(Bitmap.CompressFormat.JPEG)
            quality(100)
        }
        dismissProgressDialog()
        onReady(compressedImage)
    }
}

inline fun <reified T> Gson.toJsonList(list: List<T>): String {
    return this.toJson(list)
}

inline fun <reified T> Gson.fromJsonList(json: String): List<T> {
    val type = object : TypeToken<List<T>>() {}.type
    return this.fromJson(json, type)
}

inline fun <reified T> T.toJson(): String {
    return Gson().toJson(this)
}

inline fun <reified T> String.toObject(): T {
    return Gson().fromJson(this, T::class.java)
}

inline fun <reified T> Activity.intentToObject(key: String): T {
    return intent.getStringExtra(key)!!.toObject()
}