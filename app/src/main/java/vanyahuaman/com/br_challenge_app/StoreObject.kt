package vanyahuaman.com.br_challenge_app

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "store_table")
data class StoreObject(
        @PrimaryKey(autoGenerate = true)
        var uid:Int = 0,
        @ColumnInfo(name = "address")
        var address:String,
        @ColumnInfo(name = "city")
        var city:String,
        @ColumnInfo(name = "name")
        var name:String,
        @ColumnInfo(name = "latitude")
        var latitude:String,
        @ColumnInfo(name = "zipcode")
        var zipcode:String,
        @ColumnInfo(name = "storeLogoURL")
        var storeLogoURL:String? = null,
        @ColumnInfo(name = "phone")
        var phone:String,
        @ColumnInfo(name = "longitude")
        var longitude:String,
        @ColumnInfo(name = "storeID")
        var storeID:String,
        @ColumnInfo(name = "state")
        var state:String):Serializable

