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
import com.example.deliveryapp.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.navBottom,navController)
        NavigationUI.setupActionBarWithNavController(this, navController)



        val pref = getSharedPreferences("info", Context.MODE_PRIVATE)
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

            R.id.logout-> {
                //logout code
                val pref = getSharedPreferences("info", Context.MODE_PRIVATE)
                pref.edit().putBoolean("connected", false).apply()
            }

        }
        return super.onOptionsItemSelected(item)

    }



}
