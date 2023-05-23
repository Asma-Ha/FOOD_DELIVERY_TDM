package com.example.deliveryapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cartItem")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id_cartItem : Int? = null,
    val quantity: Int,
    val id_res : Int,
    val name_menu: String,
    val img_menu: String,
    val price_menu : Double
)
