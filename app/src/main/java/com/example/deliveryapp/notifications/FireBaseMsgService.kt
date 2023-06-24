package com.example.deliveryapp.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.deliveryapp.R
import com.example.deliveryapp.activities.MainActivity
import com.example.deliveryapp.services.AuthenticationService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


const val channelId = "notification_channel"
const val channelName = "com.example.deliveryapp.notifications"

class FireBaseMsgService : FirebaseMessagingService(){

    //generate the notification
    fun generateNotif(title : String, message : String) {

        //when clicking on it, user will be directed to the app
        val intent = Intent(this, MainActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
                    or if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else 0)

        //setting the channel name and id
        var builder = NotificationCompat.Builder(this,
        channelId).setSmallIcon(R.drawable.cart)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentText(message)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)


        //builder = builder.setContent(getRemoteView(title, message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= 26) {
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())
    }

    //attach notif to layout

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("Notif", "Notif")
        generateNotif(message.notification!!.title!!, message.notification!!.body!!)
    }


    //display notif

   /* fun getRemoteView(title : String, message : String) : RemoteViews{
        val remoteView = RemoteViews("com.example.deliveryapp", R.layout.notification)

        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.desc, message)

        remoteView.setImageViewResource(R.id.logo, R.drawable.cart)

        return remoteView
    } */


    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("MyFirebaseMessaging", "newTokenIsCalled")
        //check if user is connected
        val pref = applicationContext.getSharedPreferences("info", Context.MODE_PRIVATE)
        val conn = pref.getBoolean("connected", false)

        if(conn) {
            //get user id

        }
        else {
            Log.d("MyFirebaseMessaging", "User not loggedin");
        }


    }
}