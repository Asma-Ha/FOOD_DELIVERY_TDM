package com.example.deliveryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.R
import com.example.deliveryapp.adapters.MenuAdapter
import com.example.deliveryapp.databinding.FragmentListMenuBinding
import com.example.deliveryapp.models.Menu
import com.example.deliveryapp.viewModels.MainViewModel

class ListMenuFragment : Fragment() {
    lateinit var binding: FragmentListMenuBinding
    val data = listOf(
        Menu(R.drawable.pizza, "Margherita Pizza", 8.99, "Fresh tomato sauce, mozzarella cheese, and basil on a thin crust", "veg2340"),
        Menu(R.drawable.pastapot, "Spaghetti Carbonara", 12.99, "Spaghetti with a creamy sauce made of eggs, bacon, and Parmesan cheese", "veg2340"),
        Menu(
                R.drawable.salad,
                "Caprese Salad",
                7.99,
                "Fresh tomatoes, mozzarella cheese, and basil drizzled with balsamic glaze",
                "veg2340"
        ),
        Menu(R.drawable.risotto, "Mushroom Risotto", 15.99, "Creamy risotto with mixed mushrooms and Parmesan cheese", "veg2340"),
        Menu(R.drawable.cannoli, "Cannoli", 3.99, "Crisp pastry shell filled with sweet ricotta cheese and chocolate chips", "veg2340"))

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

        val id_res = arguments?.getString("id")
        binding.recyclerView.adapter = MenuAdapter(getRestaurantData(id_res), requireActivity())


    }


    fun getRestaurantData(id : String?) : List<Menu> {
        return data.filter { it.restaurant_id == id }
    }

}