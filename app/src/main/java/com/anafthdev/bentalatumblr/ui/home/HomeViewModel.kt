package com.anafthdev.bentalatumblr.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.anafthdev.bentalatumblr.data.model.db.DrinkHistory
import com.anafthdev.bentalatumblr.data.repository.DrinkHistoryRepository
import com.anafthdev.bentalatumblr.data.repository.UserProfileRepository
import com.anafthdev.bentalatumblr.foundation.base.ui.BaseViewModel
import com.anafthdev.bentalatumblr.foundation.common.mission.MissionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val drinkHistoryRepository: DrinkHistoryRepository,
    private val missionManager: MissionManager,
    savedStateHandle: SavedStateHandle
): BaseViewModel<HomeState>(
    savedStateHandle = savedStateHandle,
    defaultState = HomeState()
) {

    init {
        viewModelScope.launch {
            drinkHistoryRepository.getDaily(System.currentTimeMillis()).collect { histories ->
                val todayProgress = histories.sumOf { history -> history.bottle.volume }

                updateState {
                    copy(
                        drinkHistories = histories.sortedByDescending { it.date },
                        drinkProgress = todayProgress.roundToInt()
                    )
                }
            }
        }

        viewModelScope.launch {
            userProfileRepository.getUserProfile.collect { userProfile ->
                updateState {
                    copy(
                        userProfile = userProfile
                    )
                }
            }
        }
    }

    fun saveHistory(history: DrinkHistory) {
        viewModelScope.launch {
            drinkHistoryRepository.insert(history)
        }
    }

    fun deleteHistory(history: DrinkHistory) {
        viewModelScope.launch {
            drinkHistoryRepository.delete(history)
        }
    }

}
