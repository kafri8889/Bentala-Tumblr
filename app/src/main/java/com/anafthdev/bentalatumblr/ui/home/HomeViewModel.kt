package com.anafthdev.bentalatumblr.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.anafthdev.bentalatumblr.data.model.db.DrinkHistory
import com.anafthdev.bentalatumblr.data.repository.DrinkHistoryRepository
import com.anafthdev.bentalatumblr.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val drinkHistoryRepository: DrinkHistoryRepository,
    savedStateHandle: SavedStateHandle
): BaseViewModel<HomeState>(
    savedStateHandle = savedStateHandle,
    defaultState = HomeState()
) {

    init {
        viewModelScope.launch {
            drinkHistoryRepository.getDaily(System.currentTimeMillis()).collect {
                updateState {
                    copy(
                        drinkHistories = it
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
