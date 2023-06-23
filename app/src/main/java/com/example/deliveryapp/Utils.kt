package com.example.deliveryapp

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.Manifest
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import com.example.deliveryapp.models.LoginCredentials
import com.example.deliveryapp.services.AuthenticationService

object Constants {
    const val URL = "https://example.com"
}

fun openMap(context : Context, lat : Double, long : Double){
    val latitude = lat
    val longitude = long
    val data = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
    val intent = Intent(Intent.ACTION_VIEW,data)
    context.startActivity(intent)
}

fun openPage(context : Context, pageId : String, platform : String?) {
    var url: Uri? = when (platform) {
        "facebook" -> Uri.parse("fb://page/$pageId")
        "instagram" -> Uri.parse("instagram://user?username=$pageId")
        else -> null
    }
    try {
        val intent = Intent(Intent.ACTION_VIEW, url)
        context.startActivity(intent)
    } catch (e : ActivityNotFoundException) {
        //open in web
        url = Uri.parse("https://www.$platform.com/$pageId")
        val webIntent = Intent(Intent.ACTION_VIEW, url)
        context.startActivity(webIntent)
    }

    }

fun openDial(context : Context, phone : String) {
    val data = Uri.parse("tel:$phone")
    val intent = Intent(Intent.ACTION_DIAL, data)
    context.startActivity(intent)
}

fun openNewMail(context : Context, mail : String) {
    val data = Uri.parse("mailto: $mail")
    val intent = Intent(Intent.ACTION_SENDTO, data)
    context.startActivity(intent)
}



fun openCamera(activityResultLauncher : ActivityResultLauncher<Intent>) {
    val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    activityResultLauncher.launch(pictureIntent)
}
fun checkPermission(context : Context, requestCode : Int) {
    val perms = arrayOf(Manifest.permission.CAMERA)

    ActivityCompat.requestPermissions(context as Activity,perms, requestCode)

}

fun login(mail : String, password : String, activity: Activity) : Boolean {
    if(mail == "test@gmail.com" && password == "test") {
       return true
   }
    return false
}

