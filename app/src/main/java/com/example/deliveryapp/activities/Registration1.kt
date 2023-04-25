package com.example.deliveryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.deliveryapp.databinding.ActivityRegistration1Binding

class Registration1 : AppCompatActivity() {
    lateinit var binding : ActivityRegistration1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistration1Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.next.setOnClickListener {
            val intent = Intent(this, Registration2::class.java)
            startActivity(intent)
            true
        }
    }
}