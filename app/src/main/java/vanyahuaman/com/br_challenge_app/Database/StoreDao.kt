package vanyahuaman.com.br_challenge_app.Database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import vanyahuaman.com.br_challenge_app.data.StoreObject

@Dao
interface StoreDao {

    @Query("SELECT * from store_table ORDER BY name ASC")
    fun allStores(): LiveData<List<StoreObject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(store: StoreObject)

    @Query("DELETE FROM store_table")
    fun deleteAll()
}