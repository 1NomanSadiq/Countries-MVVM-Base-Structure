package nom.mvvm.structure.utils.extensions

import kotlin.math.floor

fun Int.toK(): String {
    return if (this < 1000) this.toString() else "%.1fk".format(floor(this / 100.0) / 10)
}

fun String?.ifNullOrBlank(defaultValue: () -> String) = if (isNullOrBlank()) defaultValue() else this