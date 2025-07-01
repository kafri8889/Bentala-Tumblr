package com.anafthdev.bentalatumblr.data.datasource.local

import com.anafthdev.bentalatumblr.R
import com.anafthdev.bentalatumblr.foundation.common.mission.Mission
import com.anafthdev.bentalatumblr.foundation.common.mission.TimeBasedMission

object LocalMissionDataProvider {

    val drink250mlInADay = TimeBasedMission(
        id = "drink250mlInADay",
        title = R.string.mission_title_drink_250ml_in_a_day,
        description = R.string.mission_description_drink_250ml_in_a_day,
        icon = R.drawable.mission_250ml_today,
        timeType = TimeBasedMission.TimeType.Before,
        hours = 12,
        minutes = 0
    )

    val drink600mlBefore12am = TimeBasedMission(
        id = "drink600mlBefore12am",
        title = R.string.mission_title_drink_600ml_before_12am,
        description = R.string.mission_description_drink_600ml_before_12am,
        icon = R.drawable.mission_600ml_before_12am,
        timeType = TimeBasedMission.TimeType.Before,
        hours = 12,
        minutes = 0
    )

    val drink400mlAfter8pm = TimeBasedMission(
        id = "drink400mlAfter8pm",
        title = R.string.mission_title_drink_400ml_after_8pm,
        description = R.string.mission_description_drink_400ml_after_8pm,
        icon = R.drawable.mission_400ml_after_8pm,
        timeType = TimeBasedMission.TimeType.After,
        hours = 20,
        minutes = 0
    )

    val threeDayStreak = TimeBasedMission(
        id = "threeDayStreak",
        title = R.string.mission_title_3_day_streak,
        description = R.string.mission_description_3_day_streak,
        icon = R.drawable.mission_3_day_streak,
        timeType = TimeBasedMission.TimeType.After,
        hours = 20,
        minutes = 0
    )

    val sevenDayStreak = TimeBasedMission(
        id = "sevenDayStreak",
        title = R.string.mission_title_7_day_streak,
        description = R.string.mission_description_7_day_streak,
        icon = R.drawable.mission_7_day_streak,
        timeType = TimeBasedMission.TimeType.After,
        hours = 20,
        minutes = 0
    )

    val oneMonthStreak = TimeBasedMission(
        id = "oneMonthStreak",
        title = R.string.mission_title_1_month_streak,
        description = R.string.mission_description_1_month_streak,
        icon = R.drawable.mission_1_month_streak,
        timeType = TimeBasedMission.TimeType.After,
        hours = 20,
        minutes = 0
    )

    val drinkAtLeast1p5LitersADay = TimeBasedMission(
        id = "drinkAtLeast1p5LitersADay",
        title = R.string.mission_title_drink_at_least_1_point_5_liters_a_day,
        description = R.string.mission_description_drink_at_least_1_point_5_liters_a_day,
        icon = R.drawable.mission_1_5l_in_a_day,
        timeType = TimeBasedMission.TimeType.After,
        hours = 20,
        minutes = 0
    )

    val drinkAtLeast2LitersADay = TimeBasedMission(
        id = "drinkAtLeast2LitersADay",
        title = R.string.mission_title_drink_at_least_2_liters_a_day,
        description = R.string.mission_description_drink_at_least_2_liters_a_day,
        icon = R.drawable.mission_2l_in_a_day,
        timeType = TimeBasedMission.TimeType.After,
        hours = 20,
        minutes = 0
    )

    val missions: List<Mission> = listOf(
        drink250mlInADay,
        drink600mlBefore12am,
        drink400mlAfter8pm,
        threeDayStreak,
        sevenDayStreak,
        oneMonthStreak,
        drinkAtLeast1p5LitersADay,
        drinkAtLeast2LitersADay
    )

}
