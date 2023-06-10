package org.d3if3049.ParkirKu.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ParkirEntity::class], version = 1, exportSchema = false)
abstract class ParkirDb : RoomDatabase(){

    abstract val dao: ParkirDao

    companion object{

        @Volatile
        private var INSTANCE: ParkirDb? = null

        fun getInstance(context: Context): ParkirDb {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ParkirDb::class.java,
                        "Parkir.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}