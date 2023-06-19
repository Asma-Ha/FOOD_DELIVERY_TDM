package com.example.deliveryapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryapp.appDatabase
import com.example.deliveryapp.databinding.FragmentMenuNoteBinding
import com.example.deliveryapp.models.CartItem
import com.example.deliveryapp.services.CartService
import com.example.deliveryapp.viewModels.MainViewModel


class MenuNoteFragment : DialogFragment() {
    lateinit var binding : FragmentMenuNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)


        binding.addnote.setOnClickListener {

            //add item to cart
            val menu = viewModel.menu
            if(menu != null) {
                //create new orderline
                val dao = appDatabase.getInstance(requireActivity())?.getCartDao()
                if(dao == null) {
                    Log.e("DATABASE : ", "DATABASE", Exception("exception"))
                }

                //if menu is already in cart, increment quantity
                val item =  CartService(dao).getCartItem(menu.name_menu, menu.id_res)
                if (item != null) {
                    try {
                        CartService(dao).updateCartItemQuantity(item, viewModel.quantity)
                        if(!binding.note.text.toString().isBlank()) {
                            //if there is a new note : update it
                            CartService(dao).updateCartItemNote(item, binding.note.text.toString())
                        }
                    } catch (e: Exception) {
                        Log.e("Cart Exception", "Error updating cart item", e)
                    }

                }

                //else create a new cartItem
                else{
                    val cartItem = CartItem(id_res = menu.id_res,
                        name_menu = menu.name_menu,
                        price_menu = menu.price_menu,
                        img_menu = menu.img_menu,
                        note = binding.note.getText().toString(),
                        quantity = viewModel.quantity)
                    try {
                        CartService(dao).addItemtoCart(cartItem)
                        Toast.makeText(context, "item added to cart", Toast.LENGTH_LONG).show()
                        dismiss()
                    } catch (e: Exception) {
                        Log.e("Cart Exception", "Error adding cart item", e)
                        Toast.makeText(context, "Error : cart contains items from another restaurant", Toast.LENGTH_LONG).show()
                        dismiss()
                    }
                }
            }

        }

        binding.cancelnote.setOnClickListener {
            Toast.makeText(context, "canceled", Toast.LENGTH_LONG).show()
            dismiss()
        }
    }


}