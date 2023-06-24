package com.example.deliveryapp.services

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.deliveryapp.models.Order
import com.example.deliveryapp.retrofit.RestaurantEndpoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderService {
    val error = MutableLiveData<String>()
    val id_order = MutableLiveData<String>()
    val success = MutableLiveData<String>()


    fun addOrder(order: Order) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RestaurantEndpoint.createEndpoint().addOrder(order)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val successMessage = "successful"
                        Log.d("addOrder", successMessage)
                        id_order.value = response.body()
                        success.value = successMessage

                    }
                }
            } catch (err: Exception) {
                val errorMessage = "Une erreur s'est produite : orders"
                Log.e("addOrder", errorMessage, err)
                withContext(Dispatchers.Main) {
                    error.value = errorMessage
                }
            }
        }
}

}