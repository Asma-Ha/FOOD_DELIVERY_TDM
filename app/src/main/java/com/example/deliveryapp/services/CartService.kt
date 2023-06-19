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
            throw(e)

        }

    }

    fun removeItemFromCart(cartItem: CartItem) {
        try {
            //remove from cart
            cartDao?.deleteCartItem(cartItem);
        } catch(e : Exception) {
            Log.e("removeItemFromCart", "Error removing cartItem", e)

        }

    }

    fun getCartTotal() : Double?{
        var total : Double? = 0.0;
        try {
            total = cartDao?.getTotal()
        } catch(e : Exception) {
            Log.e("emptyCart", "Error emptying cart", e)
        }
        return total;
    }

    fun getCartItem(name : String, res_id : Int) : CartItem? {
        try {
            return cartDao?.getCartItem(name, res_id);
        } catch(e : Exception) {
            Log.e("getCartItem", "Error getting an item", e)
            return null;
        }
    }

    fun updateCartItemQuantity(cartItem: CartItem, quantity : Int) {
        cartItem.quantity += quantity;

        try {
            cartDao?.updateCartItem(cartItem)
        } catch (e : Exception) {
            Log.e("UpdateCartItemQuantity", "Error updating a cart item", e)
        }

    }

    fun updateCartItemNote(cartItem: CartItem, note : String) {
        cartItem.note = note;

        try {
            cartDao?.updateCartItem(cartItem)
        } catch (e : Exception) {
            Log.e("UpdateCartItemQuantity", "Error updating a cart item", e)
        }

    }



    fun emptyCart() {
        try {
            cartDao?.deleteAllCartItems()
        } catch(e : Exception) {
            Log.e("emptyCart", "Error emptying cart", e)
        }
    }
}