package com.example.deliveryapp.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deliveryapp.models.Menu
import com.example.deliveryapp.models.Restaurant
import com.example.deliveryapp.retrofit.RestaurantEndpoint
import kotlinx.coroutines.*

class RestaurantViewModel : ViewModel() {
    var restaurants = MutableLiveData<List<Restaurant>>()
    var menus = MutableLiveData<List<Menu>>()
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

        //Log.d("ERRORORRO", "something is wrong in body or successful")

        //IO : for net ops and I/O
        CoroutineScope(Dispatchers.IO+ exceptionHandler).launch {
            val response = RestaurantEndpoint.createEndpoint().getAllRestaurants()


            //main for interaction with UI
            withContext(Dispatchers.Main) {
                //Log.d("ERRORORRO", "something is wrong in body or successful")
                loading.value = false //hide progress bar
                if(response.isSuccessful && response.body() != null) {
                    //Log.d("ERRORORRO", "something is wrong in body or successful")
                    restaurants.value = response.body()!!.toMutableList();
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
           // Log.d("ERRORORRO", "something is wrong in body or successful")
            val response = RestaurantEndpoint.createEndpoint().getRecipes(id.toString());
            //Log.d("ERRORORRO", "something is wrong in body or successful")
            //main for interaction with UI
            withContext(Dispatchers.Main) {
                //Log.d("ERRORORRO", "something is wrong in body or successful")
                loading.value = false //hide progress bar
                if(response.isSuccessful && response.body() != null) {

                    menus.value = response.body()!!.toMutableList();
                } else {
                    err.value = "Une erreur s'est produite"
                }
            }
        }
    }
}