package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [OrderEntity::class], version = 1, exportSchema = false)
abstract class InstaBoostDatabase : RoomDatabase() {
  abstract fun orderDao(): OrderDao

  companion object {
    @Volatile
    private var INSTANCE: InstaBoostDatabase? = null

    fun getDatabase(context: Context): InstaBoostDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          InstaBoostDatabase::class.java,
          "instaboost_database"
        ).build()
        INSTANCE = instance
        instance
      }
    }
  }
}
