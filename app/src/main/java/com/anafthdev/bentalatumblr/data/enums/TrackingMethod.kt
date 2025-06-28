package com.anafthdev.bentalatumblr.data.enums

import com.anafthdev.bentalatumblr.data.enums.TrackingMethod.Auto
import com.anafthdev.bentalatumblr.data.enums.TrackingMethod.Manual


/**
 * Drink history source
 *
 * @property Auto Automatic record, calculated by difference between current bottle volume and last bottle volume
 * @property Manual Manual record, user input
 */
enum class TrackingMethod {
    Auto,
    Manual
}
