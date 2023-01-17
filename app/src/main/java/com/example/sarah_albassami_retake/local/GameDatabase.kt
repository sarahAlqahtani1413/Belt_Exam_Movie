package com.example.sarah_albassami_retake.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sarah_albassami_retake.models.GameItem


@Database(entities = [GameItem::class], version = 1, exportSchema = false)
abstract class GameDatabase: RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object{
        @Volatile  // writes to this field are immediately visible to other threads
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){  // protection from concurrent execution on multiple threads
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "students"
                ).fallbackToDestructiveMigration()  // Destroys old database on version change
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}