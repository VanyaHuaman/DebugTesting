package vanyahuaman.com.br_challenge_app.Database

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import vanyahuaman.com.br_challenge_app.data.StoreObject

class StoreViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StoreRepository = StoreRepository(application)
    val allStores: LiveData<List<StoreObject>>

    init {
        allStores = repository.allStores
    }

    fun insert(store: StoreObject) {
        repository.insert(store)
    }

    fun deleteAll(){
        repository.deleteAll()
    }

}