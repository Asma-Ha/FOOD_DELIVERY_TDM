package com.example.deliveryapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.R
import com.example.deliveryapp.activities.MainActivity2
import com.example.deliveryapp.adapters.CartAdapter
import com.example.deliveryapp.appDatabase
import com.example.deliveryapp.databinding.FragmentCartBinding
import com.example.deliveryapp.services.CartService
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
        val viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.total.text = String.format("%.2f", 7.8)


        val dao = appDatabase.getInstance(requireActivity())?.getCartDao()
        val orderLines = CartService(dao).displayAllCartItems()

        binding.recyclerView.adapter = CartAdapter(orderLines, requireActivity())
        binding.checkout.setOnClickListener {
            val pref = requireActivity().getSharedPreferences("info", Context.MODE_PRIVATE)
            val conn = pref.getBoolean("connected", false)

                if(conn == true) {
                    //start validation fragment
                    binding.root.findNavController().navigate(R.id.action_cartFragment_to_validationFragment)
                } else {
                    //change activities : go to login
                    val intent = Intent(requireActivity(),MainActivity2::class.java)
                    startActivity(intent)
                }


        }

    }



}