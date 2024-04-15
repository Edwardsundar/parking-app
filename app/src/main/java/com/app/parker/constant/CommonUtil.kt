package com.app.parker.constant

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.parker.R
import com.app.parker.constant.Constants.KEY_IS_LOGGED_IN
import com.app.parker.constant.Constants.PREF_NAME

object CommonUtil {
    fun isLoggedIn(context : Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setLoggedInStatus(context: Context, isLoggedIn: Boolean) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun vehicleTypeToDrawable(vehicleType: VehicleType) =
        when(vehicleType){
            VehicleType.Bike -> R.drawable.img_bike
            VehicleType.Car -> R.drawable.img_car
            VehicleType.Van -> R.drawable.img_bus
        }
    lateinit var appContext: Context
}