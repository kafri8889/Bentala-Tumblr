package com.anafthdev.bentalatumblr.ui.statistic

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.anafthdev.bentalatumblr.data.StatisticTimeType
import com.anafthdev.bentalatumblr.data.datasource.local.LocalDrinkHistoryDataProvider
import com.anafthdev.bentalatumblr.data.model.db.DrinkHistory
import com.anafthdev.bentalatumblr.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class StatisticViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseViewModel<StatisticState>(
    savedStateHandle = savedStateHandle,
    defaultState = StatisticState()
) {

    private val drinkHistories = MutableStateFlow(emptyList<DrinkHistory>())
    private val drinkHistoriesCompare = MutableStateFlow(emptyList<DrinkHistory>())

    init {
        viewModelScope.launch {
            drinkHistories.update { LocalDrinkHistoryDataProvider.generateDummyDrinkData() }
        }

        viewModelScope.launch {
            drinkHistoriesCompare.update { LocalDrinkHistoryDataProvider.generateDummyDrinkData() }
        }

        viewModelScope.launch {
            drinkHistories.collect { newHistories ->
                calculateChartData(newHistories)
            }
        }

        viewModelScope.launch {
            drinkHistoriesCompare.collect { newHistories ->
                calculateChartData(newHistories, true)
            }
        }
    }

    private fun calculateChartData(
        histories: List<DrinkHistory> = drinkHistories.value,
        isForCompareChart: Boolean = false
    ) {
        val selectedTimeType = state.value.statisticTimeType
        val calendar = Calendar.getInstance()

        val filteredHistories = histories.filter { history ->
            if (isForCompareChart) history.date in state.value.startDateCompare..state.value.endDateCompare
            else history.date in state.value.startDate..state.value.endDate
        }

        var averageVolume = 0.0
        var averageCompletion = 0
        var drinkFrequency = 0
        var totalVolume = 0.0

        val result: List<Pair<String, Double>> = when (selectedTimeType) {
            StatisticTimeType.Daily -> {
                val hourlyData = (0..23).associate { hour ->
                    String.format("%02d", hour) to mutableListOf<DrinkHistory>()
                }.toMutableMap()

                filteredHistories.forEach { history ->
                    calendar.timeInMillis = history.date
                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
                    val hourKey = String.format("%02d", hour)
                    hourlyData[hourKey]?.add(history)
                }

                averageVolume = 0.0
                averageCompletion = 0
                drinkFrequency = 0

                hourlyData.map { (hour, list) ->
                    val totalVolume = list.sumOf { it.bottle.volume }
                    hour to totalVolume
                }
            }

            StatisticTimeType.Monthly -> {
                val cal = Calendar.getInstance().apply {
                    // ambil start date buat referensi bulan saat ini
                    timeInMillis = if (isForCompareChart) state.value.startDateCompare
                    else state.value.startDate
                }

                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

                val dailyData = (1..maxDay).associateWith {
                    mutableListOf<DrinkHistory>()
                }.toMutableMap()

                filteredHistories.forEach { history ->
                    calendar.timeInMillis = history.date
                    val y = calendar.get(Calendar.YEAR)
                    val m = calendar.get(Calendar.MONTH)
                    val d = calendar.get(Calendar.DAY_OF_MONTH)

                    // Cek kalo tahun sama bulannya sama, tambahin
                    if (y == year && m == month) {
                        dailyData[d]?.add(history)
                    }
                }

                var oneMonthVolume = 0.0
                var oneMonthPercentage = 0.0
                var oneMonthFrequency = 0

                dailyData.map { (day, list) ->
                    var totalVolume = 0.0
                    var totalGoal = 0.0

                    list.forEach {
                        totalVolume += it.bottle.volume
                        totalGoal += it.goal
                        oneMonthFrequency++
                    }

                    val avgGoalNotSafe = try {
                        totalGoal / list.size
                    } catch (_: Exception) { 1.0 }

                    val avgGoal = avgGoalNotSafe.let {
                        if (it.isNaN()) 1.0 else it
                    }
                    val percentage = (totalVolume / avgGoal) * 100

                    oneMonthPercentage += percentage
                    oneMonthVolume += totalVolume

                    day.plus(1).toString() to percentage
                }.also {
                    averageVolume = try {
                        oneMonthVolume / dailyData.size
                    } catch (_: Exception) { 0.0 }

                    averageCompletion = try {
                        ceil(oneMonthPercentage / dailyData.size).toInt()
                    } catch (_: Exception) { 0 }

                    drinkFrequency = try {
                        oneMonthFrequency / dailyData.size
                    } catch (_: Exception) { 0 }

                    totalVolume = oneMonthVolume
                }
            }

            StatisticTimeType.Yearly -> {
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)

                val monthlyData = (0..11).associateWith { mutableListOf<DrinkHistory>() }.toMutableMap()

                var historiesSize = 0
                var totalVolumeInThisYear = 0.0

                var totalCompletionInMonth = 0.0

                histories.forEach { history ->
                    calendar.timeInMillis = history.date
                    val y = calendar.get(Calendar.YEAR)
                    val m = calendar.get(Calendar.MONTH)

                    if (y == currentYear) {
                        monthlyData[m]?.add(history)
                        totalVolumeInThisYear += history.bottle.volume
                        historiesSize++
                    }
                }

                monthlyData.map { (monthIndex, list) ->
                    val monthLabel = SimpleDateFormat("MMM", Locale.getDefault())
                        .format(Calendar.getInstance().apply { set(Calendar.MONTH, monthIndex) }.time)

                    val totalVolume = list.sumOf { it.bottle.volume }
                    val totalGoal = list.sumOf { it.goal }

                    val percentage = if (totalGoal == 0.0) 0.0 else (totalVolume / totalGoal) * 100

                    totalCompletionInMonth += percentage

                    monthLabel to percentage
                }.also {
                    averageVolume = try {
                        totalVolumeInThisYear / historiesSize
                    } catch (_: Exception) { 0.0 }

                    averageCompletion = try {
                        (totalCompletionInMonth / 12.0).toInt()
                    } catch (_: Exception) { 0 }

                    drinkFrequency = try {
                        ceil(historiesSize.toDouble() / 365.0).toInt()
                    } catch (_: Exception) { 0 }
                }
            }

            StatisticTimeType.Weekly -> emptyList()
        }

        if (isForCompareChart) {
            updateState {
                copy(
                    chartYCompare = result.map { it.second.coerceIn(0.0, 100.0) }.toImmutableList(), // biar gak over,
                    totalVolumeCompare = totalVolume,
                    averageCompletionCompare = averageCompletion
                )
            }
        } else {
            updateState {
                copy(
                    // Coerce supaya tidak melebihi 100%, kalo mw dibikin lebih juga boleh
                    // misalnya nanti dikasih guideline kalo melebihi, biar user tau
                    chartY = result.map { it.second.coerceIn(0.0, 100.0) }.toImmutableList(), // biar gak over
                    averageVolume = averageVolume,
                    averageCompletion = averageCompletion,
                    drinkFrequency = drinkFrequency,
                    totalVolume = totalVolume
                )
            }
        }
    }

    fun setStatisticTimeType(type: StatisticTimeType) {
        viewModelScope.launch {
            val cal = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 1)
            }

            val startDate = cal.timeInMillis
            val startDateCompare = cal.apply { add(Calendar.MONTH, -1) }.timeInMillis
            val endDate = cal.apply {
                add(Calendar.MONTH, 1)
                set(Calendar.DAY_OF_MONTH, getMaximum(Calendar.DAY_OF_MONTH))
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 999)
            }.timeInMillis

            val endDateCompare = cal.apply {
                add(Calendar.MONTH, -1)
            }.timeInMillis

            updateState {
                copy(
                    statisticTimeType = type,
                    startDate = startDate,
                    endDate = endDate,
                    startDateCompare = startDateCompare,
                    endDateCompare = endDateCompare
                )
            }

            calculateChartData()
            calculateChartData(drinkHistoriesCompare.value, true)
        }
    }

    fun setInitialDateRange(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            updateState {
                copy(
                    startDate = startDate,
                    endDate = endDate,
                    startDateCompare = startDate - 86400000,
                    endDateCompare = endDate - 86400000
                )
            }

            calculateChartData()
            calculateChartData(drinkHistoriesCompare.value, true)
        }
    }

    fun setDateRange(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            updateState {
                copy(
                    startDate = startDate,
                    endDate = endDate
                )
            }

            calculateChartData()
        }
    }

    fun setCompareDateRange(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            updateState {
                copy(
                    startDate = startDate,
                    endDate = endDate
                )
            }

            calculateChartData(drinkHistoriesCompare.value, true)
        }
    }

    fun setStartDate(startDate: Long) {
        viewModelScope.launch {
            updateState {
                copy(
                    startDate = startDate
                )
            }

            calculateChartData()
        }
    }

    fun setEndDate(endDate: Long) {
        viewModelScope.launch {
            updateState {
                copy(
                    endDate = endDate
                )
            }

            calculateChartData()
        }
    }
}
