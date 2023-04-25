package com.example.deliveryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.R
import com.example.deliveryapp.adapters.RestaurantAdapter
import com.example.deliveryapp.databinding.FragmentListRestaurantBinding
import com.example.deliveryapp.models.Restaurant

class ListRestaurantFragment : Fragment() {
    lateinit var binding: FragmentListRestaurantBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentListRestaurantBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = RestaurantAdapter(loadData(), requireActivity())

    }


    fun loadData():List<Restaurant> {
        val data = mutableListOf<Restaurant>()
        data.add(
            Restaurant(
                "veg2340", R.drawable.vegetalien, "vegetalien",
                "Italien", 4.5, "Alger, Alger", 36.719212,2.993493,
                20, "0777777777", "jb_chetibi@esi.com",
                 "https://www.instagram.com/aarrhhccuuoobb/")
        )
        data.add(
        Restaurant(
            "sushi_avenue", R.drawable.sushi, "Sushi Avenue",
            "Japanese", 4.8, "123 Main St, San Francisco, CA", 37.7749, -122.4194,
            100, "415-555-1234", "info@sushiavenue.com",
            "https://www.instagram.com/sushiavenue_sf/"
        ))

        data.add(
        Restaurant(
            "dosa_hut", R.drawable.dosa, "Dosa Hut",
            "Indian", 3.3, "456 6th St, Chicago, IL", 41.8781, -87.6298,
            80, "312-555-5678", "info@dosahut.com",
            "https://www.instagram.com/dosahut_chicago/"
        ))
        data.add(
        Restaurant(
            "taco_fiesta", R.drawable.taco, "Taco Fiesta",
            "Mexican", 4.0, "789 Main St, Austin, TX", 30.2672, -97.7431,
            75, "512-555-9012", "info@tacofiesta.com",
            "https://www.instagram.com/tacofiesta_atx/"
        ))
        data.add(
        Restaurant(
            "happybobba", R.drawable.cafe, "Bagel Bar",
            "coffeeshop", 4.6, "987 5th Ave, New York, NY", 40.7831, -73.9712,
            120, "212-555-3456", "info@happybobba.com",
            "https://www.instagram.com/happybobba/"
        ))
        data.add(
        Restaurant(
            "pasta_paradise", R.drawable.pasta, "Pasta Paradise",
            "Italian", 2.8, "321 Oak St, San Francisco, CA", 37.7749, -122.4194,
            90, "415-555-7890", "info@pastaparadise.com",
            "https://www.instagram.com/pastaparadise_sf/"
        ))




        return data
    }


}