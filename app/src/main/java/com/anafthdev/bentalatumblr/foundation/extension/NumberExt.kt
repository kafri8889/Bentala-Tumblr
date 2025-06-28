package com.anafthdev.bentalatumblr.foundation.extension

fun Float.asInteger(): Number {
    // Cast the float to an Int. This truncates the decimal part.
    val intValue = toInt()
    // Compare the original float with the integer value.
    // If they are equal, it means the float had no fractional part.
    return if (this == intValue.toFloat()) intValue else this
}

fun Double.asInteger(): Number {
    // Cast the float to an Int. This truncates the decimal part.
    val intValue = toInt()
    // Compare the original float with the integer value.
    // If they are equal, it means the float had no fractional part.
    return if (this == intValue.toDouble()) intValue else this
}
