package com.example.deliveryapp.services

import android.util.Log
import com.example.deliveryapp.dao.CartDao
import com.example.deliveryapp.models.CartItem
import com.example.deliveryapp.models.Menu

class CartService(val cartDao: CartDao?) {

    fun displayAllCartItems(): List<CartItem>{
        try {
                val cartContent = cartDao?.getCartItems()
                if (cartContent != null) {
                    return cartContent
                }
        } catch(e :Exception){
            Log.e("displayAllCartItems", "Error retrieving the cart content ", e)
        }
        return emptyList()
    }

    fun deleteItemFromCart(cartItem: CartItem) {
        try {
            cartDao?.deleteCartItem(cartItem)
        } catch(e : Exception) {
            Log.e("deleteItemFromCart", "Error deleting cartItem", e)
        }

    }

    fun addItemtoCart(cartItem: CartItem) {
        try {
            //verify id restaurant
            val samplefromcart = cartDao?.getCartItems()
            if(samplefromcart.isNullOrEmpty()) {
                cartDao?.addCartItem(cartItem)
            } else if (samplefromcart?.get(0)?.id_res == cartItem.id_res) {

                //empty cart or same restaurant
                 cartDao?.addCartItem(cartItem)

            } else {
                //throw error
                throw Exception("Not the same restaurant")
            }


        } catch(e : Exception) {
            Log.e("addItemtoCart", "Error adding cartItem", e)

        }

    }
    fun emptyCart() {
        try {
            cartDao?.deleteAllCartItems()
        } catch(e : Exception) {

        }
    }
}