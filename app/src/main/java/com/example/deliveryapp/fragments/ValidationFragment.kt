package com.example.deliveryapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.deliveryapp.R
import com.example.deliveryapp.appDatabase
import com.example.deliveryapp.databinding.FragmentValidationBinding
import com.example.deliveryapp.services.CartService

class ValidationFragment : Fragment() {
    lateinit var binding : FragmentValidationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentValidationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = appDatabase.getInstance(requireActivity())?.getCartDao()

        //switch default fragment to listRestaurantFragment
        val pref = requireActivity().getSharedPreferences("info", Context.MODE_PRIVATE)
        pref.edit{
            putString("fragment", "listRestaurantFragment")
        }

        binding.validate.setOnClickListener {
            //send id to review fragment

            val fragment = RestaurantReviewFragment()
            val i = 1
            val bundle = bundleOf("id_res" to i)
            fragment.arguments = bundle
            fragment.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")

            //empty the cart
            CartService(dao).emptyCart()
            //go to listRestaurant fragment
            binding.root.findNavController().navigate(R.id.action_validationFragment_to_listRestaurantFragment)
        }

        binding.cancel.setOnClickListener {
            //go back to cart
            binding.root.findNavController().navigate(R.id.action_validationFragment_to_cartFragment)
        }

    }

}