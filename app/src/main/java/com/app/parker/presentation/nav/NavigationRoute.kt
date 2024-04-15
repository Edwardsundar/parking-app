package com.app.parker.presentation.nav

sealed class NavigationRoute (val route : String){
    object LoginScreen:NavigationRoute("login")
    object SignupScreen:NavigationRoute("signup")
    object ParkingAreaListingScreen:NavigationRoute("area_listing")
    object VehicleSelectionScreen:NavigationRoute("vichle_selection")
    object ParkingAreaTopView:NavigationRoute("parking_plack_top_view")
    object PaymentScreen:NavigationRoute("payment")
    object SuccessFullBookingScreen:NavigationRoute("success_screen")
    object FailureBookingScreen:NavigationRoute("failure_screen")
    object EntryORScannerScreen:NavigationRoute("incomming_qr_scanner_screen")
    object ExitORScannerScreen:NavigationRoute("outgoing_qr_scanner_screen")
    object SettingsScreen:NavigationRoute("settings_screen")
}