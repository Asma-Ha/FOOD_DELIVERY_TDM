package com.example.deliveryapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.R
import com.example.deliveryapp.activities.LoginActivity
import com.example.deliveryapp.adapters.CartAdapter
import com.example.deliveryapp.appDatabase
import com.example.deliveryapp.databinding.FragmentCartBinding
import com.example.deliveryapp.services.CartService
import com.example.deliveryapp.viewModels.MainViewModel
import com.example.deliveryapp.viewModels.RestaurantViewModel


class CartFragment : Fragment(), CartAdapter.CartItemDeleteListener {
        lateinit var binding : FragmentCartBinding
        lateinit var restaurantViewModel : RestaurantViewModel
        var total : Double = 0.0
        var deliveryfees : Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        restaurantViewModel = ViewModelProvider(requireActivity()).get(RestaurantViewModel::class.java)

        //local database access
        val dao = appDatabase.getInstance(requireActivity())?.getCartDao()
        val orderLines = CartService(dao).displayAllCartItems()

        //if the cart isnt empty
        if(!orderLines.isEmpty()) {

            //delivery fees
            val res_id = orderLines[0].id_res

            val pref = requireActivity().getSharedPreferences("info", Context.MODE_PRIVATE)
            pref.edit().putString("id_res", res_id.toString()).apply()
            Log.d("restaurant id", ""+res_id.toString())

            //if there are items in the cart, update the delivery fees accordingly


            var delivery_fees = restaurantViewModel.getRestaurantDeliveryFee(res_id)
            if(delivery_fees != null) {
                this.deliveryfees = delivery_fees;
            }

            binding.deliveryFees.text = String.format("%.2f", this.deliveryfees)


            //total price
            val t = CartService(dao).getCartTotal()
            if( t != null) {
                total = t
            }
            binding.total.text = String.format("%.2f", total + deliveryfees)
        }

        //items list
        binding.recyclerView.adapter = CartAdapter(orderLines, requireActivity(), this)
        binding.checkout.setOnClickListener {
            if(orderLines.isEmpty()) {
                //if the cart is empty :
                Toast.makeText(requireActivity(), "Cart is Empty", Toast.LENGTH_SHORT).show()
            } else {
                val pref = requireActivity().getSharedPreferences("info", Context.MODE_PRIVATE)
                val conn = pref.getBoolean("connected", false)
                if(conn) {
                    //start validation fragment
                    binding.root.findNavController().navigate(R.id.action_cartFragment_to_validationFragment)
                } else {
                    //change activities : go to login
                    val intent = Intent(requireActivity(),LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    }

    //to update the total price once an item is deleted
    fun updateTotal(amount : Double) {
        total = total - amount;
    }

    //to listen to item deletion
    override fun onItemDelete(amount: Double) {
        updateTotal(amount)
        binding.total.text = String.format("%.2f", total + deliveryfees)
    }


}