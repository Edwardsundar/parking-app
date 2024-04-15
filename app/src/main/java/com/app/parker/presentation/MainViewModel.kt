package com.app.parker.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.parker.constant.Constants
import com.app.parker.constant.VehicleType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    private val _listingAreas  = MutableStateFlow<List<ParkingArea>>(emptyList())
    val listingAreas = _listingAreas
    var selectedPlace:ParkingArea? = null
    var selectedVehicle:VehicleType? = null
    var selectedDate:String? = null
    var selectedTime:String? = null
    var selectedParkingSlot: String? = null

    init {
        _listingAreas.value = Constants.parkingAreas
    }




    fun searchAreas(query : String) = viewModelScope.launch{
        Log.e("12345678" , query)
        if (query.isEmpty()) {
            listingAreas.value = _listingAreas.value
        }else{
            val list:MutableList<ParkingArea> = mutableListOf()
            _listingAreas.value.forEach {
                if (it.name.startsWith(query,ignoreCase = true)){
                    list.add(it)
                }
            }
            listingAreas.value = list
        }
    }


}