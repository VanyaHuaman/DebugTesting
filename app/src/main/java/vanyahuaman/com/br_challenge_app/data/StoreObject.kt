package vanyahuaman.com.br_challenge_app.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "store_table")
data class StoreObject(
        var address:String,
        var city:String,
        var name:String,
        var latitude:String,
        var zipcode:String,
        var storeLogoURL:String? = null,
        @PrimaryKey
        var phone:String,
        var longitude:String,
        var storeID:String,
        var state:String):Serializable

