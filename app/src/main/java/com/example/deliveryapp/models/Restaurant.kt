package com.example.deliveryapp.models

import androidx.room.PrimaryKey


data class Restaurant(
    val id_res : Int,
    val logo_res : String,
    val name_res : String,
    val slug : String,
    val type : String,
    //val rating : Double,
    val location : String,
    val longitude : Double,
    val latitude : Double,
    //val nbr_review : Int,
    val phone : String,
    val mail : String,
    val instagram : String,
    val facebook : String,
    )