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

    private class PopulateDbAsync internal constructor(db: StoreRoomDatabase) : AsyncTask<Void, Void, Void>() {

        val mDao: StoreDao = db.storeDao()

        override fun doInBackground(vararg params: Void): Void? {
            mDao.deleteAll()
            var store = StoreObject(0,"NA","NA","NA","NA","NA","NA","NA","NA","NA","NA")
            mDao.insert(store)
            store = StoreObject(1,"NA","NA","NA","NA","NA","NA","NA","NA","NA","NA")
            mDao.insert(store)

            return null
        }
    }

    companion object {

        var INSTANCE: StoreRoomDatabase? = null

        fun getDatabase(context: Context): StoreRoomDatabase {
            if (INSTANCE == null) {
                synchronized(StoreRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context,
                                StoreRoomDatabase::class.java, "storedatabase")
                                .addCallback(sRoomDatabaseCallback)
                                .allowMainThreadQueries()
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }

        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE!!).execute()
            }
        }
    }
}