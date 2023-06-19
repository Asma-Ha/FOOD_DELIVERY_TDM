package com.example.deliveryapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deliveryapp.appDatabase
import com.example.deliveryapp.databinding.CartListItemBinding
import com.example.deliveryapp.models.CartItem
import com.example.deliveryapp.services.CartService
import com.example.deliveryapp.url

class CartAdapter (var data:List<CartItem>, val context : Context, var cartItemDeleteListener : CartItemDeleteListener) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){


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

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.binding.apply {
            name.text = data[position].name_menu
            quantity.text = data[position].quantity.toString()
            prixUnitaire.text = data[position].price_menu.toString()
            Glide.with(context).load(url + data[position].img_menu).into(img)
            note.text = data[position].note


            //remove from cart
            remove.setOnClickListener{
                val dao = appDatabase.getInstance(context)?.getCartDao()
                if(dao == null) {
                    Log.e("DATABASE : ", "DATABASE", Exception("exception"))
                }
                try {
                    //store price in variable
                    val itemPrice = data[position].price_menu * data[position].quantity
                    //remove from local database
                    CartService(dao).removeItemFromCart(data[position])
                    CartService(dao).removeItemFromCart(data[position])


                    //remove from view :
                    val mutableData = data.toMutableList()
                    mutableData.removeAt(position)
                    data = mutableData;

                    Log.d("new data : ", data.toString());
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, data.size)

                    //update total
                    cartItemDeleteListener.onItemDelete(itemPrice)


                } catch (e: Exception) {
                    Log.e("Cart Exception", "Error removing cart item", e)
                }
            }


        }
    }

    class CartViewHolder(val binding: CartListItemBinding) : RecyclerView.ViewHolder(binding.root)


    interface CartItemDeleteListener {
        fun onItemDelete(amount : Double)
    }

}