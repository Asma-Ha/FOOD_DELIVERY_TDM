package com.example.deliveryapp.services

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.deliveryapp.models.ReviewSub

import com.example.deliveryapp.retrofit.RestaurantEndpoint

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class ReviewsService {
    val error = MutableLiveData<String>()
    val success = MutableLiveData<String>()

    fun addReview(rev : ReviewSub) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RestaurantEndpoint.createEndpoint().addReview(rev)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val successMessage = "successful"
                        Log.d("AddReview", successMessage)
                        success.value = successMessage
                    }
                }
            } catch (err: Exception) {
                val errorMessage = "Une erreur s'est produite : reviews"
                Log.e("AddReview", errorMessage, err)
                withContext(Dispatchers.Main) {
                    error.value = errorMessage
                }
            }
        }
    }
}
