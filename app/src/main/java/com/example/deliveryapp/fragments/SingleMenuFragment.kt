package com.example.deliveryapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.deliveryapp.appDatabase
import com.example.deliveryapp.customViews.QuantityPicker

import com.example.deliveryapp.databinding.FragmentSingleMenuBinding
import com.example.deliveryapp.models.CartItem
import com.example.deliveryapp.services.CartService
import com.example.deliveryapp.url
import com.example.deliveryapp.viewModels.MainViewModel

class SingleMenuFragment : Fragment() {
    lateinit var binding : FragmentSingleMenuBinding
    lateinit var quantityPicker: QuantityPicker
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSingleMenuBinding.inflate(layoutInflater)
        quantityPicker = binding.quantityPicker
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val menu = viewModel.menu
        if(menu != null) {
            binding.mealName.text = menu.name_menu

            binding.mealDesc.text = menu.desc_menu
            binding.mealPrice.text = menu.price_menu.toString() + " DA"

            Glide.with(requireActivity()).load(url + menu.img_menu).into(binding.mealImg)

            binding.quantityPicker.btnLess.setOnClickListener {
                quantityPicker.decreaseQuantity()
            }

            binding.quantityPicker.btnMore.setOnClickListener {
                quantityPicker.increaseQuantity()
            }

            binding.cartBtn.setOnClickListener {
                val quant = binding.quantityPicker.currentValue

                //create new orderline
                val dao = appDatabase.getInstance(requireActivity())?.getCartDao()
                if(dao == null) {
                    Log.e("DATABASE : ", "DATABASE", Exception("exception"))
                }

                val cartItem = CartItem(id_res = menu.id_res,
                    name_menu = menu.name_menu,
                    price_menu = menu.price_menu,
                    img_menu = menu.img_menu,
                    quantity = quant)
                try {
                    CartService(dao).addItemtoCart(cartItem)
                } catch (e: Exception) {
                    Log.e("Cart Exception", "Error adding cart item", e)
                }


            }
        }



    }



}