package vanyahuaman.com.br_challenge_app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_details.*

class DetailActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val store:StoreObject = intent.getSerializableExtra("store") as StoreObject

        val addressString =
                "${store.address}, \n" +
                        "${store.city}, " +
                        "${store.state} " +
                        "${store.zipcode}"

        detail_banner_name_textview.text = store.name
        detail_store_name.text = store.name
        detail_store_address.text = addressString
        detail_store_number.text = "Store#: ${store.storeID}"
        detail_store_phone_number.text = store.phone

        if(store.storeLogoURL != null){
            val options = RequestOptions()
                    .placeholder(R.drawable.loading_image)
                    .error(R.drawable.no_image_found)
            Glide.with(this)
                    .load(store.storeLogoURL)
                    .apply(options)
                    .into(detail_store_imageView)
        }
    }
}