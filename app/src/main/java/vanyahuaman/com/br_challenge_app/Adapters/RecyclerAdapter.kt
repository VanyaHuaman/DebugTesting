package vanyahuaman.com.br_challenge_app.Adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_item.view.*
import vanyahuaman.com.br_challenge_app.Activities.DetailActivity
import vanyahuaman.com.br_challenge_app.R
import vanyahuaman.com.br_challenge_app.data.StoreObject


class RecyclerAdapter(storesArray: MutableList<StoreObject>,activity: Activity): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    var stores = storesArray
    private lateinit var context:Context
    val passedActivity = activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        context = parent.context
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return stores.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val addressString =
                "${stores[position].address}, \n" +
                        "${stores[position].city}, " +
                        "${stores[position].state} " +
                        "${stores[position].zipcode}"

        holder.storeName.text = stores[position].name
        holder.storeNumber.text = stores[position].phone
        holder.storeAddress.text = addressString
        holder.storeID.text = "Store# ${stores[position].storeID}"

        //loading image from URL
        if(stores[position].storeLogoURL != null){
            val options = RequestOptions()
                    .error(R.drawable.no_image_found)
            Glide.with(context)
                    .load(stores[position].storeLogoURL)
                    .apply(options)
                    .into(holder.storeImage)
        }

        holder.parentLayout.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                    passedActivity,holder.storeImage,"storeLogo")
            intent.putExtra("store",stores[position])
            context.startActivity(intent,options.toBundle())
        }
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val parentLayout = itemView.item_root
        val storeName = itemView.item_store_name
        val storeID = itemView.item_store_num
        val storeNumber = itemView.item_store_phone
        val storeAddress = itemView.item_store_address
        val storeImage = itemView.item_image
    }


}