package com.app.parker.repository

import android.widget.Toast
import com.app.parker.constant.CommonUtil
import com.app.parker.data.ParkerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ParkerRepository @Inject constructor(
    private val api : ParkerApi
) {

    suspend fun createNewUser(
        name: String , password : String
    ) : Boolean{
        try {
            return api.singUp(name, password)
        }catch (e : Exception){
            withContext(Dispatchers.Main.immediate){
                Toast.makeText(CommonUtil.appContext, "Server Network Error", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    suspend fun isUserExist(
        name: String , password : String
    ): Boolean{
        try {
            return api.singIn(name, password)
        }catch(e : Exception){
            withContext(Dispatchers.Main.immediate){
                Toast.makeText(CommonUtil.appContext, "Server Network Error", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    suspend fun bookNewSlot(
        place: String , time: String ,
        type: String , name: String ,
    ): Boolean{
        try {
            return api.bookNewSlot(place ,time , type , name)
        }catch (e : Exception){
            withContext(Dispatchers.Main.immediate){
                Toast.makeText(CommonUtil.appContext, "Server Network Error", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    suspend fun userCanEnter(
        place: String , name : String , type : String
    ) : Boolean {
        try {
            return api.userCanEnter(place , name , type )
        }catch (e : Exception){
            withContext(Dispatchers.Main.immediate){
                Toast.makeText(CommonUtil.appContext, "Server Network Error", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    suspend fun userCanExit(
        name : String , place: String
    ) : String {
        try {
            return api.userCanExit(name , place  )
        }catch (e : Exception){
            withContext(Dispatchers.Main.immediate){
                Toast.makeText(CommonUtil.appContext, "Server Network Error", Toast.LENGTH_SHORT).show()
            }
        }
        return ""
    }

    suspend fun userTimeConsume(
        name : String , place: String
    ) : String {
        try {
            return api.userTime(name , place )
        }catch (e : Exception){
            withContext(Dispatchers.Main.immediate){
                Toast.makeText(CommonUtil.appContext, "Server Network Error", Toast.LENGTH_SHORT).show()
            }
        }
        return ""
    }

    suspend fun getAllParkingAreaJson( place : String , name : String , time : String) : ParkingPlace {
        try {
            return api.getParkingAreaJson(place = place, name = name , time = time)
        }catch (e : Exception){
            withContext(Dispatchers.Main.immediate){
                Toast.makeText(CommonUtil.appContext, "Server Network Error", Toast.LENGTH_SHORT).show()
            }
        }
        return ParkingPlace(0,0 , 0, 0)
    }
}



data class ParkingPlace(
    val v0 : Int,
    val v1 : Int,
    val v2 : Int,
    val v3 : Int
)