package com.example.deliveryapp.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deliveryapp.models.Menu
import com.example.deliveryapp.models.Restaurant
import com.example.deliveryapp.models.Review
import com.example.deliveryapp.retrofit.RestaurantEndpoint
import kotlinx.coroutines.*

//this is a cache object to keep the delivery fees for restaurants to avoid making requests to remote server
object DeliveryFeesCache {
    private val amounts: MutableMap<Int, Double> = mutableMapOf()

    fun getDeliveryFee(res_id: Int): Double? {
        return amounts[res_id]
    }

    fun cacheDeliveryFee(res_id: Int, price: Double) {
        amounts[res_id] = price
    }

    fun isEmpty() : Boolean{
        return amounts.isEmpty();
    }
}



class RestaurantViewModel : ViewModel() {
    var restaurants = MutableLiveData<List<Restaurant>>()
    var menus = MutableLiveData<List<Menu>>()
    var reviews = MutableLiveData<List<Review>>()



    val loading = MutableLiveData<Boolean>()
    val err = MutableLiveData<String>()

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        CoroutineScope(Dispatchers.Main).launch   {
            loading.value = false
            err.value = "Une erreur s'est produite"
        }
    }

    fun loadRestaurants() {
        if(restaurants.value == null){
            //if there are no restaurants : show progress bar
            loading.value = true
        }
        //IO : for net ops and I/O
        CoroutineScope(Dispatchers.IO+ exceptionHandler).launch {
            val response = RestaurantEndpoint.createEndpoint().getAllRestaurants()

            //main for interaction with UI
            withContext(Dispatchers.Main) {

                loading.value = false //hide progress bar
                if(response.isSuccessful && response.body() != null) {
                    val resList = response.body()!!.toMutableList();
                    restaurants.value = resList
                    if(DeliveryFeesCache.isEmpty()) {
                        //cache the fees
                        for (res in resList) {
                            DeliveryFeesCache.cacheDeliveryFee(res.id_res, res.deliveryfees)
                            //Log.d(res.id_res.toString(), res.deliveryfees.toString())
                        }
                    }

                } else {
                    err.value = "Une erreur s'est produite"
                }
            }
        }
    }

    fun loadMenus(id : Int) {

        if(menus.value == null){
            loading.value = true
        }

        CoroutineScope(Dispatchers.IO+ exceptionHandler).launch {

            val response = RestaurantEndpoint.createEndpoint().getRecipes(id.toString());
            //main for interaction with UI
            withContext(Dispatchers.Main) {

                loading.value = false //hide progress bar
                if(response.isSuccessful && response.body() != null) {

                    menus.value = response.body()!!.toMutableList();
                } else {
                    err.value = "Une erreur s'est produite lors de récupération des menus"
                }
            }
        }

        //to avoid getting the prev list of menus while the new one is loading.
        menus = MutableLiveData<List<Menu>>();
    }


    fun loadReviews(id : Int) {

        if(reviews.value == null){
            loading.value = true
        }

        CoroutineScope(Dispatchers.IO+ exceptionHandler).launch {

            val response = RestaurantEndpoint.createEndpoint().getReviews(id.toString());
            //main for interaction with UI
            withContext(Dispatchers.Main) {

                loading.value = false //hide progress bar
                if(response.isSuccessful && response.body() != null) {

                    reviews.value = response.body()!!.toMutableList();
                } else {
                    err.value = "Une erreur s'est produite lors de la récupération des reviews"
                }
            }
        }

        //to avoid getting the prev list of reviews while the new one is loading.
        reviews = MutableLiveData<List<Review>>();
    }




    fun getRestaurantDeliveryFee(res_id : Int) : Double? {

        //check in cache
        var delivery_fees = DeliveryFeesCache.getDeliveryFee(res_id)
        if(delivery_fees == null) {
            Log.d("oops", "delivery fees not in cache")
            //make request to remote server
            CoroutineScope(Dispatchers.IO+ exceptionHandler).launch {
                val response = RestaurantEndpoint.createEndpoint().getDeliveryPrice(res_id.toString());
                withContext(Dispatchers.Main) {
                    if(response.isSuccessful && response.body() != null) {
                        delivery_fees = response.body()!!
                    } else {
                        err.value = "Une erreur s'est produite"
                    }
                }
            }
        }

        return delivery_fees

    }
}