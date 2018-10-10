package vanyahuaman.com.br_challenge_app

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

class StoreViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StoreRepository = StoreRepository(application)
    internal val allStores: LiveData<List<StoreObject>>

    init {
        allStores = repository.allStores
    }

    fun insert(store: StoreObject) {
        repository.insert(store)
    }


}