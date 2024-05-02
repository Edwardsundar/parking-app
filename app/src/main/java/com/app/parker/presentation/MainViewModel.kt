package com.app.parker.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.parker.constant.CommonUtil
import com.app.parker.constant.Constants
import com.app.parker.constant.VehicleType
import com.app.parker.repository.ParkerRepository
import com.app.parker.repository.ParkingPlace
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo : ParkerRepository
): ViewModel() {
    private val _listingAreas  = MutableStateFlow<List<ParkingArea>>(emptyList())
    val listingAreas:StateFlow<List<ParkingArea>> =  _listingAreas
    private val _bookingResult  = MutableStateFlow<Boolean?>(null)
    val bookingResult:StateFlow<Boolean?> =  _bookingResult
    private val _isSuccessToOpen  = MutableStateFlow<Boolean?>(null)
    val isSuccessToOpen:StateFlow<Boolean?> =  _isSuccessToOpen

    var totalAmount = mutableStateOf("")
    var totalTimeConsume = mutableStateOf("")

    private val _availablePlace  = MutableStateFlow<ParkingPlaceLoadCell?>(null)
    val availablePlace:StateFlow<ParkingPlaceLoadCell?> =  _availablePlace

    var selectedPlace:ParkingArea? = null
    var selectedVehicle:VehicleType? = null
    var selectedDate:String? = null
    var selectedTime:String? = null
    var selectedDateLocal:LocalDate? = null
    var selectedTimeLocal:LocalTime? = null
    var selectedParkingSlot: String? = null
    var bookedPlace:ParkingArea? = null

    var successEnterOrExit : MutableState<Boolean> = mutableStateOf(true)
    var failEnterOrExit : MutableState<Boolean> = mutableStateOf(false)


    var loginSuccess : MutableState<String> = mutableStateOf("")

    var userName  = ""

    init {
        userName = CommonUtil.getUserName()
        _listingAreas.value = Constants.parkingAreas
    }




    fun searchAreas(query : String) = viewModelScope.launch{
        if (query.isEmpty()) {
            _listingAreas.value = Constants.parkingAreas
        }else{
            val list:MutableList<ParkingArea> = mutableListOf()
            _listingAreas.value.forEach {
                if (it.name.startsWith(query,ignoreCase = true)){
                    list.add(it)
                }
            }
            _listingAreas.value = list
            listingAreas.value
        }
    }

    private var availablePlaceJob : Job? = null
    fun collectBookedDataFromApi() {
        if (availablePlaceJob != null) return
        val localDateTime = LocalDateTime.of(selectedDateLocal, selectedTimeLocal)
        val millies = localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli().toString()
        availablePlaceJob = viewModelScope.launch(Dispatchers.IO) {
            while (true){
                val response = repo.getAllParkingAreaJson(
                    selectedPlace!!.name ,
                    userName, millies

                    )
                _availablePlace.value = ParkingPlaceLoadCell(
                    v0 = response.v0 == 1,
                    v1 = response.v1 == 1
                )
                delay(2000)
                Log.e("12345678" ,
                    "a1 = " + availablePlace.value?.v0.toString() +
                    "  a2 = " + availablePlace.value?.v1.toString()
                )
            }
        }
    }

    fun closeBookingPlace() {
        availablePlaceJob?.cancel()
        availablePlaceJob = null
    }

    fun openGate(enter: Boolean) {
        viewModelScope.launch {
            if (enter) {
                val res = repo.userCanEnter(selectedPlace!!.name , userName , "")
                _isSuccessToOpen.value = res
            }else {
                totalTimeConsume.value = repo.userTimeConsume(userName , selectedPlace!!.name)
                totalAmount.value  = repo.userCanExit(userName , selectedPlace!!.name)
            }
        }
    }

    val isLogInSuccess = mutableStateOf(false)
    private val _isLogInSuccessFlow = MutableStateFlow(false)
    val isLogInSuccessFlow  = _isLogInSuccessFlow

    suspend fun logIn(name : String , password : String) : Boolean {
        val emailPattern = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+$")
        if (!emailPattern.matches(name)){
            loginSuccess.value = "Plese Enter Valid Email"
            return false
        }
        val job = viewModelScope.launch {
            if (repo.isUserExist(name , password)){
                isLogInSuccess.value = true
                loginSuccess.value = "Log In Success full !!!"
                _isLogInSuccessFlow.value = true
                CommonUtil.setLoggedInStatus(isLoggedIn = true , userName =  name)
            }else {
                isLogInSuccess.value = false
                loginSuccess.value = "Log In Failed Please Verify the Password And User Name !!!"
            }
        }
        job.join()
        return isLogInSuccess.value
    }

    fun signUp(userName : String , password: String , confirmPassword : String){
        val emailPattern = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+$")
        if (!password.equals(confirmPassword)){
            loginSuccess.value = "Plese Enter password and confirm Password as Same"
            return
        }
        if (!emailPattern.matches(userName)){
            loginSuccess.value = "Plese Enter Valid Email"
            return
        }
        viewModelScope.launch {
            if (repo.createNewUser(userName , password)){
                isLogInSuccess.value = true
                CommonUtil.setLoggedInStatus(isLoggedIn = true , userName =  userName)
            }
        }
    }

    fun bookNewSlot(){

        val localDateTime = LocalDateTime.of(selectedDateLocal, selectedTimeLocal)
        val millies = localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli().toString()
        viewModelScope.launch {
            _bookingResult.value = repo.bookNewSlot(selectedPlace!!.name , millies , selectedParkingSlot!! , userName )
        }
    }

    fun setBookResultNull() {
        _bookingResult.value = null
    }

    fun setSuccessToOpenNull() {
        _isSuccessToOpen.value = null
    }
}

data class ParkingPlaceLoadCell(
    val v0 : Boolean,
    val v1 : Boolean
)