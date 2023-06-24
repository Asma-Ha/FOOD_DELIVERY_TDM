package com.example.deliveryapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.deliveryapp.R

import com.example.deliveryapp.databinding.ActivityMainBinding
import com.example.deliveryapp.services.AuthenticationService

import com.example.deliveryapp.viewModels.MainViewModel

import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialize the viewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //for bottom navigation view (menu) and action bar (top)

        //find the current fragment within the activity
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        //navcontroller to navigate in the graph
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.navBottom,navController)
        NavigationUI.setupActionBarWithNavController(this, navController)


        //notif token generation

        val pref = getSharedPreferences("info", Context.MODE_PRIVATE)
        val conn = pref.getBoolean("connected", false)
        if(conn) {
            //user connected
            getToken(pref.getString("id", "-1") ?: "-1");
        } else {
            Log.d("MyFirebaseMessaging", "User not loggedin");
        }

        //to choose which fragment to go to after the validation fragment
            //if cancelled : cart
            //if validated : listRestaurants

        var frag : String = pref.getString("fragment", "listRestaurantFragment") ?: "listRestaurantFragment"

        navController.navigate(resources.getIdentifier(frag, "id", packageName))

    }


    //toolbar menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            //the logout icon on top
            R.id.logout-> {
                //logout code
                val pref = getSharedPreferences("info", Context.MODE_PRIVATE)
                pref.edit().putBoolean("connected", false).apply()
            }

        }
        return super.onOptionsItemSelected(item)

    }

    private fun getToken(id : String) {
        //get token from firebase
        //FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
                task ->
            if(!task.isSuccessful) {
                Log.e("MyFirebaseMessaging", "Cant get FCM token", task.exception)
            } else {
                val pref = getSharedPreferences("info", Context.MODE_PRIVATE)
                AuthenticationService().postToken(id, task.result.toString())
                pref.edit().putString("token", task.result.toString())
                Log.d("MyFirebaseMessaging", "New Token: " + task.result.toString());
            }
        }
    }


}
