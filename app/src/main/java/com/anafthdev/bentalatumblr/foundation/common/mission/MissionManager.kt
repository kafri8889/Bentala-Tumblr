package com.anafthdev.bentalatumblr.foundation.common.mission

import com.anafthdev.bentalatumblr.data.model.db.DrinkHistory
import com.anafthdev.bentalatumblr.data.model.db.MissionProgress
import javax.inject.Inject

class MissionManager @Inject constructor() {

    private val missions: ArrayList<Mission> = arrayListOf()
    private val progresses: ArrayList<MissionProgress> = arrayListOf()

    private var onProgressChangedListener: OnProgressChangedListener? = null

    /**
     * Submit drink history
     *
     * @return Modified/new mission progresses
     */
    fun submit(history: DrinkHistory) {
        val modifiedProgresses = ArrayList<MissionProgress>(progresses)

        for (mission in missions) {
            val result = mission.onDrink(history)

            if (result.isValid) {
                val existedIndex = modifiedProgresses.indexOfFirst { it.id == mission.id }
                val existedProgress = modifiedProgresses.getOrNull(existedIndex) ?: MissionProgress(
                    id = mission.id,
                    progress = 0f
                )

                result.apply(existedProgress).let { modifierProgress ->
                    if (existedIndex >= 0) modifiedProgresses[existedIndex] = modifierProgress
                    else modifiedProgresses.add(modifierProgress)
                }
            }
        }

        onProgressChangedListener?.onProgressChanged(progresses)
    }

    /**
     * Register a mission
     */
    fun register(mission: Mission) {
        missions.add(mission)
    }

    fun setMissionProgresses(progresses: List<MissionProgress>) {
        this.progresses.clear()
        this.progresses.addAll(progresses)
    }

    fun setOnProgressChangedListener(listener: OnProgressChangedListener) {
        onProgressChangedListener = listener
    }

    fun interface OnProgressChangedListener {
        fun onProgressChanged(progresses: List<MissionProgress>)
    }

}
