package com.anafthdev.bentalatumblr.data.model.db

import android.os.Parcelable
import androidx.annotation.FloatRange
import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Merepresentasikan sebuah progress dari sebuah mission.
 *
 * @property id ID dari progress.
 * @property progress Persentase progress dari mission.
 */
@Entity(tableName = "mission_progress")
@Immutable
@Parcelize
@Serializable
data class MissionProgress(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,

    @param:FloatRange(from = 0.0, to = 1.0)
    @ColumnInfo(name = "progress") var progress: Float
): Parcelable {

    val isCompleted: Boolean
        get() = progress >= 1f

}
