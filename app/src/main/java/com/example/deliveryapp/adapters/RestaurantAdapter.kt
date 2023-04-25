package com.example.deliveryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.*
import com.example.deliveryapp.databinding.RestaurantListItemBinding
import com.example.deliveryapp.models.Restaurant


class RestaurantAdapter(val data:List<Restaurant>, val context : Context):RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RestaurantListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            img.setImageResource(data[position].logo)
            name.text = data[position].name
            resType.text = data[position].type
            resAdr.text = data[position].location
            nbReviews.text = data[position].nbr_review.toString()
            ratings.rating = data[position].rating.toFloat()


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
                val bundle = bundleOf("id" to data[position].restaurant_id)
                Navigation.findNavController(holder.binding.root).navigate(R.id.action_listRestaurantFragment_to_listMenuFragment, bundle)
            }

        }

    }


    class MyViewHolder(val binding: RestaurantListItemBinding) : RecyclerView.ViewHolder(binding.root)

}



