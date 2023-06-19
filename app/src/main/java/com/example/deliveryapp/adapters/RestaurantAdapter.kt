package com.example.deliveryapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deliveryapp.*
import com.example.deliveryapp.databinding.RestaurantListItemBinding
import com.example.deliveryapp.models.Restaurant
import com.example.deliveryapp.viewModels.MainViewModel


class RestaurantAdapter(val context : Context):RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>() {

    var data = mutableListOf<Restaurant>()

    fun setRestaurants(restaurants : List<Restaurant>) {
        this.data = restaurants.toMutableList()
        notifyDataSetChanged() //notify the observers that data has changed
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RestaurantListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
           // img.setImageResource(data[position].logo_res)
            Glide.with(context).load(url + data[position].logo_res).into(img)
            //Log.d("IMAGE", url + data[position].logo_res)
            name.text = data[position].name_res
            resType.text = data[position].type
            resAdr.text = data[position].location



            mapIcon.setOnLongClickListener {
                openMap(context, data[position].longitude, data[position].latitude)
                true
            }

            instaIcon.setOnLongClickListener {
                openPage(context, data[position].instagram, "instagram")
                true
            }

            phoneIcon.setOnLongClickListener {
                openDial(context, data[position].phone)
                true
            }

            mailIcon.setOnLongClickListener {
                openNewMail(context, data[position].mail)
                true
            }


            resCard.setOnClickListener {
                var viewModel = ViewModelProvider(context as ViewModelStoreOwner).get(MainViewModel::class.java)
                viewModel.res_name = data[position].name_res
                viewModel.res_img = data[position].logo_res
                val bundle = bundleOf("id" to data[position].id_res)
                Navigation.findNavController(holder.binding.root).navigate(R.id.action_listRestaurantFragment_to_listMenuFragment, bundle)
            }

        }

    }


    class MyViewHolder(val binding: RestaurantListItemBinding) : RecyclerView.ViewHolder(binding.root)

}



