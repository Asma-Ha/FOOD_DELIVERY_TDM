package com.example.deliveryapp.retrofit

import android.util.Log
import com.example.deliveryapp.models.LoginCredentials
import com.example.deliveryapp.models.User
import com.example.deliveryapp.url
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface UserEndpoint {

    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginCredentials) : Response<String>

    companion object {
        @Volatile
        var endpoint : UserEndpoint? = null
        fun createEndpoint() : UserEndpoint {
            if(endpoint == null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(UserEndpoint::class.java)
                }
            }
            Log.d("Checkpoint", "EndPoint Created")
            return endpoint!!
        }

    }

}