package com.example.deliveryapp.services

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.deliveryapp.models.LoginCredentials
import com.example.deliveryapp.models.User
import com.example.deliveryapp.retrofit.UserEndpoint
import kotlinx.coroutines.*

class AuthenticationService {
    val result = MutableLiveData<String>()

    val error = MutableLiveData<String>()

    fun login(loginCredentials: LoginCredentials) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = UserEndpoint.createEndpoint().login(loginCredentials)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {

                        result.value = response.body()
                    }
                }

            } catch (err: Exception) {
                withContext(Dispatchers.Main) {
                    error.value = "Une erreur s'est produite lors du login"
                }
            }
        }
    }

}