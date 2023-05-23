package com.example.deliveryapp.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id_user : Int? = null,
    val fullname : String,
    val phone : String,
    val mail : String,
    val photo: String,
    val password : String,
    val address : String
) : java.io.Serializable
