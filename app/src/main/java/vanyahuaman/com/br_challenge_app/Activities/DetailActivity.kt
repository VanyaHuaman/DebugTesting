package vanyahuaman.com.br_challenge_app.Activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_details.*
import vanyahuaman.com.br_challenge_app.R
import vanyahuaman.com.br_challenge_app.data.StoreObject

class DetailActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val store: StoreObject = intent.getSerializableExtra("store") as StoreObject

        val addressString =
                "${store.address}, \n" +
                        "${store.city}, " +
                        "${store.state} " +
                        "${store.zipcode}"

        title = store.name
        detail_store_name.text = store.name
        detail_store_address.text = addressString
        detail_store_number.text = "Store#: ${store.storeID}"
        detail_store_phone_number.text = store.phone
        detail_store_lat_long.text = "${store.latitude},${store.longitude}"

        detail_store_lat_long.setOnClickListener{
            openMap(this,store,addressString)
        }

        detail_store_address.setOnClickListener{
            openMap(this,store,addressString)
        }

        detail_store_phone_number.setOnClickListener{
            openCall(this,store.phone)
        }

        //loading image from URL
        if(store.storeLogoURL != null){
            val options = RequestOptions()
                    .error(R.drawable.no_image_found)
            Glide.with(this)
                    .load(store.storeLogoURL)
                    .apply(options)
                    .into(detail_store_imageView)
        }
    }

    fun openMap(context: Context, store:StoreObject,addressString:String){
        val gmmIntentUri = Uri.parse("geo:${store.latitude},${store.longitude}?q=$addressString+${store.name}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            ContextCompat.startActivity(context, mapIntent, null)
        }
    }

    fun openCall(context: Context, phoneNumber:String){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            ContextCompat.startActivity(context, intent, null)
        }
    }
}