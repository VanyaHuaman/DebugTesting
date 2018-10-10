package vanyahuaman.com.br_challenge_app

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class StoreRepository internal constructor(application: Application) {

    private val storeDao: StoreDao
    internal val allStores: LiveData<List<StoreObject>>

    init {
        val db = StoreRoomDatabaseJava.getDatabase(application)
        storeDao = db.storeDao()
        allStores = storeDao.allStores
    }

    fun insert(store: StoreObject) {
        insertAsyncTask(storeDao).execute(store)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: StoreDao) : AsyncTask<StoreObject, Void, Void>() {

        override fun doInBackground(vararg params: StoreObject): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}