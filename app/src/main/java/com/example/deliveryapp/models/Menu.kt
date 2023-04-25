package com.example.deliveryapp.models

data class Menu (
    var img : Int,
    var name : String,
    var price : Double,
    var description : String,
    var restaurant_id : String
        ) : java.io.Serializable {
            constructor() : this(0, "", 0.0, "", "")
        }