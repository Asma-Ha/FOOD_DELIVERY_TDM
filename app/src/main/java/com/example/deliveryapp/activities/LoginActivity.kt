package com.example.deliveryapp.activities


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Toast
import androidx.core.content.edit
import com.example.deliveryapp.databinding.ActivityLoginBinding
import com.example.deliveryapp.login
import com.example.deliveryapp.models.LoginCredentials
import com.example.deliveryapp.services.AuthenticationService

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authenticationService: AuthenticationService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button.setOnClickListener {
            val mail = binding.mailInputEditText.text.toString()
            val password = binding.passwordInputEditText.text.toString()

            if (mail.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter valid credentials", Toast.LENGTH_SHORT).show()
            } else {
                login(mail, password)
            }

        }
    }

    private fun login(mail :String, password : String){

        authenticationService = AuthenticationService()
        authenticationService.login(LoginCredentials(mail, password))


        authenticationService.result.observe(this){ user ->
            var conn = false;
            Log.d("USER", "user "+ user)
            if(!user.isEmpty() && user != null) {
                conn = true
            }

            val pref = getSharedPreferences("info", Context.MODE_PRIVATE)
            pref.edit{
                putBoolean("connected", conn)
                putString("fragment", "validationFragment")
                putString("id", user)
            }

            if(conn) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Log.d("wep", "wep")
                Toast.makeText(this, "Invalid credentialssss", Toast.LENGTH_SHORT).show()
            }
        }
    }
}