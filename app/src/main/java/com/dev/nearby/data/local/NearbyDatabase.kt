package com.dev.nearby.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VenueEntity::class], version = 1, exportSchema = true)
abstract class NearbyDatabase : RoomDatabase() {

    internal abstract fun nearByLocalSource(): NearbyLocalDataSource

    companion object {
        private const val DB_NAME = "nearby.db"
        fun get(context: Context): NearbyDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                NearbyDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}