package com.example.deliveryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.MenuListItemBinding
import com.example.deliveryapp.models.Menu
import com.example.deliveryapp.viewModels.MainViewModel


class MenuAdapter(val data:List<Menu>, val context : Context): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            MenuListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        holder.binding.apply {
            menuImg.setImageResource(data[position].img)
            menuName.setText(data[position].name)

            menuPrice.setText(data[position].price.toString())
            menuItem.setOnClickListener {
                var viewModel = ViewModelProvider(context as ViewModelStoreOwner).get(MainViewModel::class.java)
                viewModel.menu = data[position]
                holder.binding.root.findNavController().navigate(R.id.singleMenuFragment)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class MenuViewHolder(val binding: MenuListItemBinding) : RecyclerView.ViewHolder(binding.root)

}