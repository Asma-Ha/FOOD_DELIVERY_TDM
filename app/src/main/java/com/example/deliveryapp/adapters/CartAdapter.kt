package com.example.deliveryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.databinding.CartListItemBinding
import com.example.deliveryapp.models.Menu
import com.example.deliveryapp.models.OrderLine
import com.example.deliveryapp.viewModels.MainViewModel

class CartAdapter (val data:List<OrderLine>, val context : Context) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){
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
            name.text = data[position].menu.name
            quantity.text = data[position].quantity.toString()
            prixUnitaire.text = data[position].menu.price.toString()
            img.setImageResource(data[position].menu.img)
        }
    }

    class CartViewHolder(val binding: CartListItemBinding) : RecyclerView.ViewHolder(binding.root)

}