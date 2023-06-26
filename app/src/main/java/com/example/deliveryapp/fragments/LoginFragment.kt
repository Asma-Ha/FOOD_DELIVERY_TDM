package com.example.deliveryapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.navigation.findNavController
import com.example.deliveryapp.R

import com.example.deliveryapp.activities.MainActivity
import com.example.deliveryapp.databinding.FragmentLoginBinding
import com.example.deliveryapp.models.LoginCredentials
import com.example.deliveryapp.services.AuthenticationService

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var authenticationService: AuthenticationService
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.button.setOnClickListener {
            val mail = binding.mailInputEditText.text.toString()
            val password = binding.passwordInputEditText.text.toString()

            if (mail.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter valid credentials", Toast.LENGTH_SHORT).show()
            } else {
                login(mail, password)
            }
        }

        binding.sign.setOnClickListener {
            //go to signup fragment
            binding.root.findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

    private fun login(mail :String, password : String){

        authenticationService = AuthenticationService()
        authenticationService.login(LoginCredentials(mail, password))


        authenticationService.result.observe(requireActivity()){ user ->
            var conn = false
            if(!user.isEmpty() && user != null) {
                conn = true
            }

            val pref = requireActivity().getSharedPreferences("info", Context.MODE_PRIVATE)
            pref.edit{
                putBoolean("connected", conn)
                putString("fragment", "validationFragment")
                putString("id", user)
            }

            if(conn) {
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                //Log.d("wep", "wep")
                Toast.makeText(requireActivity(), "Invalid credentialssss", Toast.LENGTH_SHORT).show()
            }
        }
    }


}