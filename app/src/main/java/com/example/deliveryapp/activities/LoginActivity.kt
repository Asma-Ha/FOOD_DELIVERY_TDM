package com.example.deliveryapp.activities


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.core.content.edit
import androidx.navigation.findNavController
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.ActivityMain2Binding
import com.example.deliveryapp.fragments.ValidationFragment
import com.example.deliveryapp.login

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button.setOnClickListener {
            val mail = binding.mailInputEditText.text.toString()
            val password = binding.passwordInputEditText.text.toString()

            if (mail.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter valid credentials", Toast.LENGTH_SHORT).show()
            } else {
                val conn = login(mail, password)
                //save in local
                val pref = getSharedPreferences("info", Context.MODE_PRIVATE)
                pref.edit{
                    putBoolean("connected", conn)
                    putString("fragment", "validationFragment")
                }

                if(conn) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

}