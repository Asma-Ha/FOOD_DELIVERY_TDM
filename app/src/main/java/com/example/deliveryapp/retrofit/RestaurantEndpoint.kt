package com.example.deliveryapp.retrofit

import android.util.Log
import com.example.deliveryapp.models.*
import com.example.deliveryapp.url
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RestaurantEndpoint {

    @GET("restaurants/getAll")
    suspend fun getAllRestaurants() : Response<List<Restaurant>>

    @GET("restaurants/{id}/getRecipes")
    suspend fun getRecipes(@Path("id") id: String): Response<List<Menu>>

    @GET("restaurants/{id}/getDeliveryPrice")
    suspend fun getDeliveryPrice(@Path("id") id: String): Response<Double>

    @GET("restaurants/{id}/getReviews")
    suspend fun getReviews(@Path("id") id: String) : Response<List<Review>>

    @POST("restaurants/reviews")
    suspend fun addReview(@Body rev : ReviewSub) : Response<String>
    //singleton

    @POST("orders/")
    suspend fun addOrder(@Body order : Order) : Response<String>
    companion object {
        @Volatile
        var endpoint : RestaurantEndpoint? = null
        fun createEndpoint() : RestaurantEndpoint {
            if(endpoint == null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(RestaurantEndpoint::class.java)
                }
            }
            Log.d("Checkpoint", "EndPoint Created")
            return endpoint!!
        }

    }
}