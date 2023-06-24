package com.example.deliveryapp.services

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.deliveryapp.models.LoginCredentials
import com.example.deliveryapp.models.Token
import com.example.deliveryapp.models.User
import com.example.deliveryapp.retrofit.UserEndpoint
import kotlinx.coroutines.*
import retrofit2.http.Body

class AuthenticationService {
    var result = MutableLiveData<String>()

    val error = MutableLiveData<String>()

    fun login(loginCredentials: LoginCredentials) {
        result = MutableLiveData<String>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("USER LOGIN FFS", "h")
                val response = UserEndpoint.createEndpoint().login(loginCredentials)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        result.value = response.body()
                    } else {
                        result.value= ""
                    }
                }

            } catch (err: Exception) {
                withContext(Dispatchers.Main) {
                    error.value = "Une erreur s'est produite lors du login"
                }
            }
        }
    }

    fun postToken(id : String, token : String) {
        Log.d("MyFirebaseMessaging", "bef token registered")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = UserEndpoint.createEndpoint().updateToken(id, Token(token))
                withContext(Dispatchers.Main) {

                    if (response.isSuccessful) {
                        Log.d("MyFirebaseMessaging ", "token registered")
                    }
                }

            } catch (err: Exception) {
                withContext(Dispatchers.Main) {
                    error.value = "Une erreur s'est produite : Token update"
                    Log.d("MyFirebaseMessaging", "token not registered"+err.message)
                }
            }
        }
    }

}