package com.example.deliveryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryapp.customViews.QuantityPicker

import com.example.deliveryapp.databinding.FragmentSingleMenuBinding
import com.example.deliveryapp.models.OrderLine
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
        val info = viewModel.menu
        binding.mealName.text = info.name
        binding.mealImg.setImageResource(info.img)
        binding.mealDesc.text = info.description
        binding.mealPrice.text = info.price.toString() + " DA"

        binding.quantityPicker.btnLess.setOnClickListener {
            quantityPicker.decreaseQuantity()
        }

        binding.quantityPicker.btnMore.setOnClickListener {
            quantityPicker.increaseQuantity()
        }

        binding.cartBtn.setOnClickListener {
            val quant = binding.quantityPicker.currentValue
            val order = OrderLine(menu = info, quantity = quant)
            viewModel.cart.orders.add(order)
            viewModel.cart.total = viewModel.cart.total + info.price * quant
        }

    }



}