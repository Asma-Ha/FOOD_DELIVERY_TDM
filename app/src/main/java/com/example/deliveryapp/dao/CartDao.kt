package com.example.deliveryapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.deliveryapp.models.CartItem

@Dao
interface CartDao {
    @Insert
    fun addCartItem(cartItem: CartItem)

    @Delete
    fun deleteCartItem(cartItem: CartItem)


    @Query("SELECT * FROM cartItem")
    fun getCartItems(): List<CartItem>

    @Query("DELETE FROM cartItem")
    fun deleteAllCartItems()

}