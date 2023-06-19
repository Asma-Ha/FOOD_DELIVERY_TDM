package com.example.deliveryapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.deliveryapp.R
import com.example.deliveryapp.adapters.MenuAdapter
import com.example.deliveryapp.adapters.RestaurantAdapter
import com.example.deliveryapp.databinding.FragmentListMenuBinding
import com.example.deliveryapp.models.Menu
import com.example.deliveryapp.url
import com.example.deliveryapp.viewModels.MainViewModel
import com.example.deliveryapp.viewModels.RestaurantViewModel

class ListMenuFragment : Fragment() {
    lateinit var binding: FragmentListMenuBinding
    lateinit var restaurantViewModel : RestaurantViewModel
    lateinit var adapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentListMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        restaurantViewModel = ViewModelProvider(requireActivity()).get(RestaurantViewModel::class.java)
        this.adapter = MenuAdapter(requireActivity())
        binding.recyclerView.adapter = this.adapter

        var viewModel = ViewModelProvider(context as ViewModelStoreOwner).get(MainViewModel::class.java)
        binding.resName.text = viewModel.res_name
        Glide.with(requireActivity()).load(url + viewModel.res_img).into(binding.resImg)

        //get restaurant id to show the menus
        val id_res = arguments?.getInt("id")
        if (id_res != null) {
            //Log.d("ID RES : ", "id res is " + id_res);
            restaurantViewModel.loadMenus(id_res);
        }


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
            Toast.makeText(requireContext(), err, Toast.LENGTH_SHORT).show()
        }

        //restaurants list
        restaurantViewModel.menus.observe(requireActivity()){ menus ->
            adapter.setMenus(menus)
        }
    }


}