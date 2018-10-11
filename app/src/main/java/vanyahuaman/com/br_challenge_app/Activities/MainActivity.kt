package vanyahuaman.com.br_challenge_app.Activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import vanyahuaman.com.br_challenge_app.R
import vanyahuaman.com.br_challenge_app.Adapters.RecyclerAdapter
import vanyahuaman.com.br_challenge_app.Database.StoreViewModel
import vanyahuaman.com.br_challenge_app.data.StoreObject
import java.io.IOException


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView:RecyclerView
    lateinit var storeViewModel: StoreViewModel
    val databaseURL =
            "http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/stores.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Stores"
        recyclerView = recycler_view
        recyclerView.layoutManager = LinearLayoutManager(this)

        storeViewModel =
                ViewModelProviders.of(this@MainActivity).get(StoreViewModel::class.java)

        if(networkAvailable()){
            if(insertData()){
                loadDataToRecyclerView()
            }
        }else{
            loadDataToRecyclerView()
            Snackbar.make(recyclerView,
                    "NO INTERNET CONNECTION",
                    Snackbar.LENGTH_LONG).show()
        }
    }

    fun loadDataToRecyclerView(){
        storeViewModel
            .allStores
            .observe(this@MainActivity, Observer<List<StoreObject>>() {
                if(it != null){
                    recyclerView.adapter = RecyclerAdapter(it as MutableList<StoreObject>)
                    recyclerView.adapter.notifyDataSetChanged()
                }
            })
    }

    fun insertData():Boolean{
        var JsonActive:Boolean = false
        val request = Request.Builder().url(databaseURL).build()
        val client = OkHttpClient()
        loadDataToRecyclerView()

        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                Snackbar.make(recyclerView,
                        "Json Call Failed",
                        Snackbar.LENGTH_SHORT)
                        .setAction("RETRY") {
                            insertData()
                        }.show()
            }

            override fun onResponse(call: Call, response: Response) {
                //takes Json response and inserts objects into Room Database
                if(response.isSuccessful){
                    storeViewModel.deleteAll()
                    val body = response.body()?.string()
                    val gson = GsonBuilder().create()
                    val jsonArray = gson.fromJson(body, StoreJsonArray::class.java)
                    for (store in jsonArray.stores){
                        storeViewModel.insert(store)
                    }
                    JsonActive = true
                }else{
                    jsonNotFound()
                    JsonActive = false
                }
            }
        })
        return JsonActive
    }

    //takes in Json
    class StoreJsonArray(val stores:MutableList<StoreObject>)

    //Checks status of internet connection
    fun networkAvailable():Boolean {
        val connectivityManager:ConnectivityManager
                = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo:NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun jsonNotFound(){
        Snackbar.make(recyclerView,
                "JSON NOT FOUND",
                Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY") {
                    insertData()
                }.show()
    }
}

