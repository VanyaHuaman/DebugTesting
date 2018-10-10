package vanyahuaman.com.br_challenge_app

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface StoreDao {

    @get:Query("SELECT * from store_table")
    val allStores: LiveData<List<StoreObject>>

    @Insert
    fun insert(store: StoreObject)

    @Query("DELETE FROM store_table")
    fun deleteAll()
}