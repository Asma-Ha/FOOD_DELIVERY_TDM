package com.example.deliveryapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.R
import com.example.deliveryapp.adapters.MenuAdapter
import com.example.deliveryapp.adapters.RestaurantAdapter
import com.example.deliveryapp.databinding.FragmentListRestaurantBinding
import com.example.deliveryapp.models.Restaurant
import com.example.deliveryapp.retrofit.RestaurantEndpoint
import com.example.deliveryapp.viewModels.RestaurantViewModel
import kotlinx.coroutines.*

class ListRestaurantFragment : Fragment() {
    lateinit var binding: FragmentListRestaurantBinding
    lateinit var restaurantViewModel : RestaurantViewModel
    lateinit var adapter: RestaurantAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentListRestaurantBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restaurantViewModel = ViewModelProvider(requireActivity()).get(RestaurantViewModel::class.java)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        this.adapter = RestaurantAdapter(requireActivity())
        binding.recyclerView.adapter = this.adapter


        restaurantViewModel.loadRestaurants()

        //progress bar
        restaurantViewModel.loading.observe(requireActivity()) { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        //errors
        restaurantViewModel.err.observe(requireActivity()) {err ->
            Toast.makeText(requireContext(), err,Toast.LENGTH_SHORT).show()

        }

        //restaurants list
        restaurantViewModel.restaurants.observe(requireActivity()){ restaurants ->
            adapter.setRestaurants(restaurants)
        }

    }



    /*fun loadData():List<Restaurant> {
        val data = mutableListOf<Restaurant>()
        data.add(
            Restaurant(
                1, R.drawable.vegetalien, "vegetalien",
                "italian", 4.5, "Alger, Alger", 36.719212,2.993493,
                20, "0777777777", "jb_chetibi@esi.com",
                 "https://www.instagram.com/aarrhhccuuoobb/")
        )
        data.add(
        Restaurant(
            2, R.drawable.sushi, "Sushi Avenue",
            "Japanese", 4.8, "123 Main St, San Francisco, CA", 37.7749, -122.4194,
            100, "415-555-1234", "info@sushiavenue.com",
            "https://www.instagram.com/sushiavenue_sf/"
        ))

        data.add(
        Restaurant(
            3, R.drawable.dosa, "Dosa Hut",
            "Indian", 3.3, "456 6th St, Chicago, IL", 41.8781, -87.6298,
            80, "312-555-5678", "info@dosahut.com",
            "https://www.instagram.com/dosahut_chicago/"
        ))
        data.add(
        Restaurant(
            4, R.drawable.taco, "Taco Fiesta",
            "Mexican", 4.0, "789 Main St, Austin, TX", 30.2672, -97.7431,
            75, "512-555-9012", "info@tacofiesta.com",
            "https://www.instagram.com/tacofiesta_atx/"
        ))
        data.add(
        Restaurant(
            5, R.drawable.cafe, "Bagel Bar",
            "coffeeshop", 4.6, "987 5th Ave, New York, NY", 40.7831, -73.9712,
            120, "212-555-3456", "info@happybobba.com",
            "https://www.instagram.com/happybobba/"
        ))
        data.add(
        Restaurant(
            6, R.drawable.pasta, "Pasta Paradise",
            "Italian", 2.8, "321 Oak St, San Francisco, CA", 37.7749, -122.4194,
            90, "415-555-7890", "info@pastaparadise.com",
            "https://www.instagram.com/pastaparadise_sf/"
        ))




        return data
    }*/



}