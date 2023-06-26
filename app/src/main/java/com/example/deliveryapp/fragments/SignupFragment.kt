package com.example.deliveryapp.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.example.deliveryapp.activities.MainActivity
import com.example.deliveryapp.checkPermission

import com.example.deliveryapp.databinding.FragmentSignupBinding
import com.example.deliveryapp.models.SignupCredentials
import com.example.deliveryapp.openCamera
import com.example.deliveryapp.services.AuthenticationService
import java.io.ByteArrayOutputStream

class SignupFragment : Fragment() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    val requestCode = 400

    lateinit var binding : FragmentSignupBinding
    var base64String : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentSignupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result : ActivityResult ->
            val intent = result.data
            if(result.resultCode == AppCompatActivity.RESULT_OK && intent != null) {
                val imageBitmap = intent.extras?.get("data") as Bitmap
                val resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, 300, 300, true)
                binding.userImg.setImageBitmap(resizedBitmap)


                val stream = ByteArrayOutputStream()
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()
                base64String = Base64.encodeToString(byteArray, Base64.DEFAULT)
                Log.d("Picture", base64String)

            }
        }

        binding.addpic.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera(activityResultLauncher)
            } else {
                //Ask for permission
                checkPermission(requireActivity(), requestCode)
            }
        }

        binding.button.setOnClickListener {
            val authenticationService = AuthenticationService()
            authenticationService.signup(SignupCredentials(binding.usernameInputEditText.text.toString(), binding.mailInputEditText.text.toString(), base64String, binding.passwordInputEditText.text.toString()))

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


    override fun onRequestPermissionsResult(permsRequestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grantResults)
        if (permsRequestCode==requestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera(activityResultLauncher)
        }
    }

}