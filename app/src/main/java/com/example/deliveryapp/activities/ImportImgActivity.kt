package com.example.deliveryapp.activities

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import com.example.deliveryapp.databinding.ActivityImportImgBinding
import androidx.core.content.ContextCompat
import android.Manifest
import android.graphics.Bitmap
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.deliveryapp.checkPermission
import com.example.deliveryapp.openCamera

class ImportImgActivity : AppCompatActivity() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    val requestCode = 400

    lateinit var binding : ActivityImportImgBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImportImgBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result : ActivityResult ->
            val intent = result.data
            if(result.resultCode == RESULT_OK && intent != null) {
                val imageBitmap = intent.extras?.get("data") as Bitmap
                val resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, 300, 300, true)
                binding.userImg.setImageBitmap(resizedBitmap)
        }
        }

        binding.addPic.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera(activityResultLauncher)
            } else {
                //Ask for permission
                checkPermission(this, requestCode)
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