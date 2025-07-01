package com.anafthdev.bentalatumblr.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anafthdev.bentalatumblr.data.datasource.local.dao.DrinkBottleDao
import com.anafthdev.bentalatumblr.data.datasource.local.dao.DrinkHistoryDao
import com.anafthdev.bentalatumblr.data.datasource.local.dao.MissionProgressDao
import com.anafthdev.bentalatumblr.data.datasource.local.dao.ReminderDao
import com.anafthdev.bentalatumblr.data.model.db.DrinkBottle
import com.anafthdev.bentalatumblr.data.model.db.DrinkHistory
import com.anafthdev.bentalatumblr.data.model.db.MissionProgress
import com.anafthdev.bentalatumblr.data.model.db.Reminder

@Database(
    entities = [
        MissionProgress::class,
        DrinkBottle::class,
        DrinkHistory::class,
        Reminder::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(DatabaseTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun missionProgressDao(): MissionProgressDao
    abstract fun drinkHistoryDao(): DrinkHistoryDao
    abstract fun drinkBottleDao(): DrinkBottleDao
    abstract fun reminderDao(): ReminderDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context = context,
                        klass = AppDatabase::class.java,
                        name = "app_database"
                    ).fallbackToDestructiveMigration(true).build()
                }
            }

            return INSTANCE!!
        }
    }

}
