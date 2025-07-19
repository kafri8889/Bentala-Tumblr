package com.anafthdev.bentalatumblr.data.datasource.local

import com.anafthdev.bentalatumblr.R
import com.anafthdev.bentalatumblr.data.model.AccessoryItem

object LocalAccessoryItemDataProvider {

    val whiteTumblr = AccessoryItem(
        id = "tumblr-white",
        name = "White Tumblr",
        price = 1000000,
        point = 1_000,
        imageUrl = "",
        imageResId = R.drawable.bentala_tumblr_2
    )

    val blackTumblr = AccessoryItem(
        id = "tumblr-black",
        name = "Black Tumblr",
        price = 1000000,
        point = 1_000,
        imageUrl = "",
        imageResId = R.drawable.bentala_tumblr_3
    )

    val greenTumblr = AccessoryItem(
        id = "tumblr-green",
        name = "Green Tumblr",
        price = 1000000,
        point = 1_000,
        imageUrl = "",
        imageResId = R.drawable.bentala_tumblr_4
    )

    val values = listOf(
        whiteTumblr,
        blackTumblr,
        greenTumblr
    )

}
