package com.example.deliveryapp.dao

import androidx.room.*
import com.example.deliveryapp.models.CartItem

@Dao
interface CartDao {
    @Insert
    fun addCartItem(cartItem: CartItem)

    @Delete
    fun deleteCartItem(cartItem: CartItem)


    @Query("SELECT * FROM cartItem")
    fun getCartItems(): List<CartItem>

    @Query("SELECT * FROM cartItem WHERE name_menu = :name AND id_res = :res_id")
    fun getCartItem(name : String, res_id : Int) : CartItem


    @Update
    fun updateCartItem(cartItem: CartItem)

    @Query("DELETE FROM cartItem")
    fun deleteAllCartItems()

    @Query("SELECT SUM(price_menu * quantity) AS total FROM cartItem")
    fun getTotal() : Double

}