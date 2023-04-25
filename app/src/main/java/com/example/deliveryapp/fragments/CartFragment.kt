package com.example.deliveryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.R
import com.example.deliveryapp.adapters.CartAdapter
import com.example.deliveryapp.databinding.FragmentCartBinding
import com.example.deliveryapp.models.Menu
import com.example.deliveryapp.viewModels.MainViewModel


class CartFragment : Fragment() {
        lateinit var binding : FragmentCartBinding

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
        var viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.total.text = String.format("%.2f", viewModel.cart.total)
        binding.recyclerView.adapter = CartAdapter(viewModel.cart.orders, requireActivity())

    }



}