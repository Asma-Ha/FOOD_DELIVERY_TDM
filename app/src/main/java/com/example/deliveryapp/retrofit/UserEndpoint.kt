package com.example.deliveryapp.retrofit

import android.util.Log
import com.example.deliveryapp.models.LoginCredentials
import com.example.deliveryapp.models.SignupCredentials
import com.example.deliveryapp.models.Token
import com.example.deliveryapp.url
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path


interface UserEndpoint {

    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginCredentials) : Response<String>

    @POST("notifications/{id}")
    suspend fun updateToken(
        @Path("id") id: String,
        @Body token: Token
    ): Response<String>

    @POST("users/signup")
    suspend fun signup(@Body signupCredentials: SignupCredentials) : Response<String>
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