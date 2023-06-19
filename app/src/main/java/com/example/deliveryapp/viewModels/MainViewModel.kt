package com.example.deliveryapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.deliveryapp.models.Menu
import com.example.deliveryapp.models.Restaurant

class MainViewModel() : ViewModel() {
    var menu : Menu? = null
    var quantity : Int= 0;

    //to display res info in menus page
    var res_name : String? = null;
    var res_img : String? = null;
}