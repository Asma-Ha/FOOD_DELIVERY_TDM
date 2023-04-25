package com.example.deliveryapp.models

data class Restaurant(
    var restaurant_id : String,
    var logo : Int,
    var name : String,
    var type : String,
    var rating : Double,
    var location : String,
    var longitude : Double,
    var latitude : Double,
    var nbr_review : Int,
    var phone : String,
    var mail : String,
    var instagram : String,

    )