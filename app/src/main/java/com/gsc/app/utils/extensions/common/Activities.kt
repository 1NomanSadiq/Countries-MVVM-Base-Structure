package com.gsc.app.utils.extensions.common

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.properties.ReadOnlyProperty

inline fun <reified T> Activity.intentToObject(
    defaultValue: T? = null,
    key: String? = null
): ReadOnlyProperty<Any, T> =
    ReadOnlyProperty { _, property ->
        Gson().fromJson(
            intent.getStringExtra(key ?: property.name),
            T::class.java
        ) ?: defaultValue!!
    }

inline fun AppCompatActivity.launchAndRepeatWithViewLifecycle(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(lifecycleState) {
            block()
        }
    }
}

fun Activity.toast(text: CharSequence?, duration: Int = Toast.LENGTH_SHORT): Unit =
    Toast.makeText(this, text ?: "null", duration).show()

inline fun <reified T : Any> Activity.start(extras: Intent.() -> Unit) {
    val intent = intentFor<T>(this)
    intent.apply(extras)
    startActivity(intent)
}

inline fun <reified T : Any> Activity.start() {
    val intent = intentFor<T>(this)
    startActivity(intent)
}

inline fun <reified T : Any> Activity.startActivityForResult(
    requestCode: Int,
    options: Bundle? = null,
    action: String? = null
) {
    startActivityForResult(intentFor<T>(this).setAction(action), requestCode, options)
}

fun Activity.getNavController(@IdRes navHostFragmentId: Int): NavController {
    val navHostFragment =
        (this as FragmentActivity).supportFragmentManager.findFragmentById(navHostFragmentId) as NavHostFragment
    return navHostFragment.navController
}

fun AppCompatActivity.replaceFragment(
    containerViewId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = true
) {
    val fragmentManager = supportFragmentManager
    val tag =
        fragment::class.java.simpleName  // Use the simple name of the fragment's class as the tag
    // Check if the fragment is already in the stack
    val existingFragment = fragmentManager.findFragmentByTag(tag)
    val fragmentTransaction = fragmentManager.beginTransaction()
    if (existingFragment == null) {
        // The fragment is not in the stack, add it
        fragmentTransaction.replace(containerViewId, fragment, tag)
        if (addToBackStack)
            fragmentTransaction.addToBackStack(tag)
    } else {
        // The fragment is in the stack, show it and remove others above it
        fragmentManager.popBackStack(tag, 0)
    }
    fragmentTransaction.commit()
}