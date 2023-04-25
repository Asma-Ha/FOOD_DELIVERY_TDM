package com.example.deliveryapp.models

import android.graphics.Bitmap

data class User(
    var fullname : String,
    var phone : String,
    var photo: Bitmap,
    var password : String,
    var address : String
) : java.io.Serializable
