package com.example.deliveryapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.deliveryapp.models.Order
import com.example.deliveryapp.services.CartService
import com.example.deliveryapp.services.OrderService

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
            val pref = requireActivity().getSharedPreferences("info", Context.MODE_PRIVATE)
            val i = pref.getString("id_res", "-1") ?: "-1"
            val resid = i.toIntOrNull() ?: -1
            //Log.d("estaurant id", i)
            val bundle = bundleOf("id_res" to i)
            fragment.arguments = bundle
            fragment.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")

            //add order to database


            //get user id :

            val conn = pref.getBoolean("connected", false)
            if(conn) {
                val id = pref.getString("id", "-1") ?: "-1"
                val id_usr = id.toIntOrNull() ?: -1
                if(id_usr != -1) {
                    val orderService =OrderService()
                    orderService.addOrder(Order(resid, id_usr, binding.addressEditText.getText().toString()))
                    orderService.error.observe(requireActivity()) {err ->
                        Toast.makeText(requireContext(), err, Toast.LENGTH_SHORT).show()
                    }

                    orderService.success.observe(requireActivity()) {success ->
                        Toast.makeText(requireContext(), success, Toast.LENGTH_SHORT).show()
                    }


                }

            }

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