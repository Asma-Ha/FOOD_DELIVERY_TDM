package com.example.deliveryapp.models

data class User(
    val id_user : Int,
    val username : String,
    val phone : String,
    val email : String,
    val image_url: String,
    val password : String,
    val address : String,
    val state : Int,
)
