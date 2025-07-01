package com.anafthdev.bentalatumblr.ui.mission

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.anafthdev.bentalatumblr.data.datasource.local.LocalMissionDataProvider
import com.anafthdev.bentalatumblr.data.model.db.MissionProgress
import com.anafthdev.bentalatumblr.data.repository.MissionProgressRepository
import com.anafthdev.bentalatumblr.foundation.base.ui.BaseViewModel
import com.anafthdev.bentalatumblr.foundation.common.mission.MissionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(
    private val missionProgressRepository: MissionProgressRepository,
    private val missionManager: MissionManager,
    savedStateHandle: SavedStateHandle
): BaseViewModel<MissionState>(
    savedStateHandle = savedStateHandle,
    defaultState = MissionState()
) {

    init {
        viewModelScope.launch {
            missionProgressRepository.getAll().collect { progresses ->
                missionManager.setMissionProgresses(progresses)
            }
        }

        missionManager.setOnProgressChangedListener { newProgresses ->
            viewModelScope.launch {
                missionProgressRepository.update(newProgresses)
            }
        }

        // Fake data
        viewModelScope.launch {
            updateState {
                copy(
                    unlockedAchievements = 2,
                    missionProgress = listOf(
                        MissionProgress(
                            id = LocalMissionDataProvider.missions[0].id,
                            progress = 1f
                        ),
                        MissionProgress(
                            id = LocalMissionDataProvider.missions[1].id,
                            progress = 1f
                        )
                    )
                )
            }
        }
    }

}
