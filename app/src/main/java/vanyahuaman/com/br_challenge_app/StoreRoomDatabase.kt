package vanyahuaman.com.br_challenge_app

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask

@Database(entities = [StoreObject::class], version = 1)
abstract class StoreRoomDatabase : RoomDatabase() {

    abstract fun storeDao(): StoreDao

    companion object {

        var INSTANCE: StoreRoomDatabase? = null

        fun getDatabase(context: Context): StoreRoomDatabase {
            if (INSTANCE == null) {
                synchronized(StoreRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context,
                                StoreRoomDatabase::class.java, "storedatabase")
                                .allowMainThreadQueries()
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }

    }
}