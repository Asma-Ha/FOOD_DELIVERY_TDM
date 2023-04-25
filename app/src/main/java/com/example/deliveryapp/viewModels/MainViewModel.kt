package com.example.deliveryapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.deliveryapp.models.Cart
import com.example.deliveryapp.models.Menu

class MainViewModel() : ViewModel() {
    var menu : Menu = Menu()
    var cart : Cart = Cart()
}