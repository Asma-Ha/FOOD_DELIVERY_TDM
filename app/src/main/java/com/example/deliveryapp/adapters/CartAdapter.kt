package com.example.deliveryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deliveryapp.databinding.CartListItemBinding
import com.example.deliveryapp.models.CartItem
import com.example.deliveryapp.url

class CartAdapter (val data:List<CartItem>, val context : Context) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            CartListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        holder.binding.apply {
            name.text = data[position].name_menu
            quantity.text = data[position].quantity.toString()
            prixUnitaire.text = data[position].price_menu.toString()
            Glide.with(context).load(url + data[position].img_menu).into(img)
        }
    }

    class CartViewHolder(val binding: CartListItemBinding) : RecyclerView.ViewHolder(binding.root)

}