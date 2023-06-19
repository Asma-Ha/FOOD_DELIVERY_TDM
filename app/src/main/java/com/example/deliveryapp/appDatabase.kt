package com.example.deliveryapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.deliveryapp.dao.CartDao
import com.example.deliveryapp.models.*

@Database(entities = [CartItem::class], version = 1)
abstract class appDatabase : RoomDatabase(){
    abstract fun getCartDao() : CartDao

    companion object {
        private var INSTANCE :appDatabase? = null

        fun getInstance(context : Context) : appDatabase? {
            if(INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(
                        context, appDatabase::class.java,
                        "delivery_app_db.db"
                    ).allowMainThreadQueries().build()
            }

            return INSTANCE
        }
    }

}