package com.anafthdev.bentalatumblr.data

import com.anafthdev.bentalatumblr.R

enum class StatisticTimeType {
    Daily,
    Weekly,
    Monthly,
    Yearly;

    val titleResId: Int
        get() = when (this) {
            Daily -> R.string.statistic_type_daily
            Weekly -> R.string.statistic_type_weekly
            Monthly -> R.string.statistic_type_monthly
            Yearly -> R.string.statistic_type_yearly
        }
}
