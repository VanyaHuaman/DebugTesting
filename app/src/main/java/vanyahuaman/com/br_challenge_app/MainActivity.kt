package vanyahuaman.com.br_challenge_app

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = recycler_view
        recyclerView.layoutManager = LinearLayoutManager(this)

        if(networkAvailable()){
            buildStoreArray()
        }else{
            Snackbar.make(recyclerView,
                    "NO INTERNET CONNECTION",
                    Snackbar.LENGTH_INDEFINITE).show()
        }

        val db:Database = Room.databaseBuilder(getApplicationContext(),Database::class.java,"storedatabase").build()


    }

    fun buildStoreArray(){

        val databaseURL =
                "http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/stores.json"

        var tempArray = mutableListOf<StoreObject>()
        val request = Request.Builder().url(databaseURL).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                Snackbar.make(recyclerView,
                        "Json Call Failed",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("RETRY", View.OnClickListener {
                            buildStoreArray()
                        }).show()
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()

                val jsonArray = gson.fromJson(body,StoreJsonArray::class.java)
                for (store in jsonArray.stores){
                    tempArray.add(store)
                }

                runOnUiThread{
                    recyclerView.adapter = RecyclerAdapter(tempArray)
                }
            }
            })
    }

    class StoreJsonArray(val stores:MutableList<StoreObject>)

    fun networkAvailable():Boolean {
        val connectivityManager:ConnectivityManager
                = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo:NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}

