package com.anafthdev.bentalatumblr.data.model.db

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anafthdev.bentalatumblr.R
import kotlinx.parcelize.Parcelize

@Entity(tableName = "drink_bottle")
@Immutable
@Parcelize
data class DrinkBottle(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    /**
     * Volume in milliliter
     */
    @ColumnInfo(name = "volume") val volume: Double,
    @ColumnInfo(name = "defaultBottle") val defaultBottle: Boolean
) : Parcelable {

    val drawableResId: Int
        get() = when (volume) {
            in 0.0..125.0 -> R.drawable.ic_cup_125ml
            in 126.0..175.0 -> R.drawable.ic_cup_175ml
            in 176.0..225.0 -> R.drawable.ic_glass_225ml
            in 226.0..300.0 -> R.drawable.ic_bottle_300ml
            in 301.0..400.0 -> R.drawable.ic_glass_400ml
            in 401.0..550.0 -> R.drawable.ic_glass_550ml
            else -> R.drawable.ic_glass_550ml
        }

}
