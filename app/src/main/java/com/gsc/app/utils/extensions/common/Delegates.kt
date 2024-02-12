package com.gsc.app.utils.extensions.common

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.MainThread
import androidx.collection.ArrayMap
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import java.lang.reflect.Method
import kotlin.reflect.KClass

internal val methodSignature = arrayOf(Bundle::class.java)
internal val methodMap = ArrayMap<KClass<out NavArgs>, Method>()
class NullableNavArgsLazy<Args : NavArgs>(
    private val navArgsClass: KClass<Args>,
    private val argumentProducer: () -> Bundle?
) : Lazy<Args?> {
    private var cached: Args? = null

    override val value: Args?
        get() {
            var args = cached
            if (args == null) {
                val arguments = argumentProducer()
                if (arguments != null) {
                    val method: Method = methodMap[navArgsClass]
                        ?: navArgsClass.java.getMethod("fromBundle", *methodSignature).also { method ->
                            // Save a reference to the method
                            methodMap[navArgsClass] = method
                        }

                    @SuppressLint("BanUncheckedReflection") // needed for method.invoke
                    @Suppress("UNCHECKED_CAST")
                    args = method.invoke(null, arguments) as Args
                    cached = args
                }
            }
            return args
        }

    override fun isInitialized(): Boolean = cached != null
}

@MainThread
inline fun <reified Args : NavArgs> Fragment.nullableNavArgs(): NullableNavArgsLazy<Args> =
    NullableNavArgsLazy(Args::class) {
        arguments
    }