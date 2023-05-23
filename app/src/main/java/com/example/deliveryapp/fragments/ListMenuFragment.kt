package com.example.deliveryapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.R
import com.example.deliveryapp.adapters.MenuAdapter
import com.example.deliveryapp.adapters.RestaurantAdapter
import com.example.deliveryapp.databinding.FragmentListMenuBinding
import com.example.deliveryapp.models.Menu
import com.example.deliveryapp.viewModels.RestaurantViewModel

class ListMenuFragment : Fragment() {
    lateinit var binding: FragmentListMenuBinding
    lateinit var restaurantViewModel : RestaurantViewModel
    lateinit var adapter: MenuAdapter
    /*val data = listOf(
        Menu(
                img_menu = R.drawable.pizza,
                name_menu = "Margherita Pizza",
                price_menu = 8.99,
                desc_menu ="Fresh tomato sauce, mozzarella cheese, and basil on a thin crust",
                id_res = 1),
        Menu(
                img_menu = R.drawable.pastapot,
                name_menu = "Spaghetti Carbonara",
                price_menu = 12.99,
                desc_menu = "Spaghetti with a creamy sauce made of eggs, bacon, and Parmesan cheese",
                id_res = 2) ,
        Menu(
                img_menu = R.drawable.salad,
                name_menu = "Caprese Salad",
                price_menu = 7.99,
                desc_menu = "Fresh tomatoes, mozzarella cheese, and basil drizzled with balsamic glaze",
                id_res = 1
        ),
        Menu(
            img_menu = R.drawable.risotto,
            name_menu = "Mushroom Risotto",
            price_menu = 15.99,
            desc_menu = "Creamy risotto with mixed mushrooms and Parmesan cheese",
            id_res = 1),
        Menu(
            img_menu = R.drawable.cannoli,
            name_menu = "Cannoli",
            price_menu = 3.99,
            desc_menu = "Crisp pastry shell filled with sweet ricotta cheese and chocolate chips",
            id_res = 1))*/


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

        val id_res = arguments?.getInt("id")
        //Log.d("ID RES : ", "id res is " + id_res);
        if (id_res != null) {
            Log.d("ID RES : ", "id res is " + id_res);
            restaurantViewModel.loadMenus(id_res);
        }



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


    /*fun getRestaurantData(id : Int?) : List<Menu> {
        return data.filter { it.id_res == id }
    }*/




}