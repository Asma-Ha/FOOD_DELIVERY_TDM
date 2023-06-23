package com.example.deliveryapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResultListener
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.FragmentMenuNoteBinding
import com.example.deliveryapp.databinding.FragmentRestaurantReviewBinding
import com.example.deliveryapp.models.Review
import com.example.deliveryapp.models.ReviewSub
import com.example.deliveryapp.services.ReviewsService


class RestaurantReviewFragment : DialogFragment() {
    lateinit var binding : FragmentRestaurantReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantReviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitReview.setOnClickListener {
            //get the user id from pref
            val pref = requireActivity().getSharedPreferences("info", Context.MODE_PRIVATE)
            val usr_id = pref.getString("id", "-1")?.toIntOrNull() ?: -1


            val id_res = arguments?.getInt("id_res") ?: -1
            Log.d("HIII", id_res.toString())

            //create a new entry in the remote database
            val reviewService = ReviewsService()
            reviewService.addReview(ReviewSub(usr_id, id_res, binding.review.getText().toString(), binding.rating.numStars.toDouble()))
            dismiss()
        }

        binding.cancelReview.setOnClickListener {
            Toast.makeText(context, "canceled", Toast.LENGTH_LONG).show()
            dismiss()
        }

    }


}