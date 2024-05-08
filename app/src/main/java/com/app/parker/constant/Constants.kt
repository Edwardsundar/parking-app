package com.app.parker.constant

import com.app.parker.presentation.ParkingArea

object Constants {
    const val PLACE_ID = "Place_ID"
    const val PREF_NAME = "MyAppPrefs"
    const val KEY_IS_LOGGED_IN = "isLoggedIn"
    const val USER_NAME = "user name"
    val parkingAreas = listOf(
        ParkingArea("Parking Area 1", "https://www.unitygroup.in/img/detail-banner01.jpg", "123 Main St, City"),
        ParkingArea("Parking Area 2", "https://hubballidharwadinfra.com/wp-content/uploads/2023/09/inox-multiplex-dharwad.jpg", "456 Elm St, City"),
        ParkingArea("Parking Area 3", "https://content.jdmagicbox.com/comp/bangalore/r2/080pxx80.xx80.170902150949.p4r2/catalogue/brookefield-mall-brookefield-bangalore-malls-ffpzsuszpx.jpg", "789 Oak St, City"),
        ParkingArea("Parking Area 4", "" , "456 Elm St, City"),
        ParkingArea("Parking Area 5", "https://via.placeholder.com/150", "789 Oak St, City"),
        ParkingArea("Parking Area 6", "https://via.placeholder.com/150", "123 Main St, City"),
        ParkingArea("Parking Area 7", "https://via.placeholder.com/150", "456 Elm St, City"),
        ParkingArea("Parking Area 8", "https://via.placeholder.com/150", "789 Oak St, City"),
        ParkingArea("Parking Area 9", "https://via.placeholder.com/150", "123 Main St, City"),
        ParkingArea("Parking Area 10", "https://via.placeholder.com/150", "456 Elm St, City")
    )

//    const val BASE_URL = "http://192.168.141.220:8080/"
    const val BASE_URL = "http://192.168.141.147:8080/"
}