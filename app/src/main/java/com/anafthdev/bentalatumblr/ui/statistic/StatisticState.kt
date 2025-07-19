package com.anafthdev.bentalatumblr.ui.statistic

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.anafthdev.bentalatumblr.data.StatisticTimeType
import com.anafthdev.bentalatumblr.data.datasource.local.LocalUserProfileDataProvider
import com.anafthdev.bentalatumblr.data.model.UserProfile
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize

/**
 * @property startDate Start date in milliseconds for filtering history
 * @property endDate End date in milliseconds for filtering history
 * @property startDateCompare Start date in milliseconds for comparing history with [startDate]
 * @property endDateCompare End date in milliseconds for comparing history with [endDate]
 * @property totalVolume Total volume in ml for the selected period
 * @property totalVolumeCompare Total volume in ml for the selected period for comparation with [totalVolume]
 * @property averageVolume Average volume in ml
 * @property averageCompletion Average completion in percent
 * @property averageCompletionCompare Average completion in percent for comparation with [averageCompletion]
 */
@Immutable
@Parcelize
data class StatisticState(
    val userProfile: UserProfile = LocalUserProfileDataProvider.dinaSartika,
    val statisticTimeType: StatisticTimeType = StatisticTimeType.Daily,
    val averageVolume: Double = 0.0,
    val averageCompletion: Int = 0,
    val averageCompletionCompare: Int = 0,
    val drinkFrequency: Int = 0,
    val totalVolume: Double = 0.0,
    val totalVolumeCompare: Double = 0.0,
    val chartX: ImmutableList<String> = persistentListOf(),
    val chartY: ImmutableList<Double> = persistentListOf(),
    val chartXCompare: ImmutableList<String> = persistentListOf(),
    val chartYCompare: ImmutableList<Double> = persistentListOf(),
    val startDate: Long = System.currentTimeMillis(),
    val endDate: Long = System.currentTimeMillis(),
    val startDateCompare: Long = System.currentTimeMillis() - 86400000,
    val endDateCompare: Long = System.currentTimeMillis() - 86400000,
): Parcelable
