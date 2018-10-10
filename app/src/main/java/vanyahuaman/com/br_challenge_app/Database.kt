package vanyahuaman.com.br_challenge_app

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [StoreObject::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun storeDao():StoreDao

}