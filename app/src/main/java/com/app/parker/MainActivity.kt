package com.app.parker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.parker.constant.CommonUtil
import com.app.parker.presentation.LoginScreen
import com.app.parker.presentation.MainViewModel
import com.app.parker.presentation.ParkingAreaList
import com.app.parker.presentation.ParkingAreaTopView
import com.app.parker.presentation.PaymentScreen
import com.app.parker.presentation.SignUpScreen
import com.app.parker.presentation.VehicleSelectionScreen
import com.app.parker.presentation.nav.NavigationRoute
import com.app.parker.ui.theme.ParkerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel() as MainViewModel
            ParkerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NavigationRoute.SignupScreen.route
//                        if (CommonUtil.isLoggedIn(this)) NavigationRoute.ParkingAreaListingScreen.route
//                        else NavigationRoute.ParkingAreaListingScreen.route
                    ){
                        composable(route = NavigationRoute.LoginScreen.route){
                            LoginScreen(navController)
                        }
                        composable(route =NavigationRoute.SignupScreen.route){
                            SignUpScreen(navController)
                        }
                        composable(NavigationRoute.ParkingAreaListingScreen.route){
                            ParkingAreaList(navController, viewModel )
                        }
                        composable(
                            NavigationRoute.VehicleSelectionScreen.route
                        ){
                            VehicleSelectionScreen(navController = navController , viewModel )
                        }
                        composable(route = NavigationRoute.ParkingAreaTopView.route){
                            ParkingAreaTopView(navController = navController, viewModel)
                        }
                        composable(route =NavigationRoute.PaymentScreen.route){
                            PaymentScreen(viewModel = viewModel){
                                this@MainActivity.onBackPressed()
                            }
                        }
                        composable(NavigationRoute.SuccessFullBookingScreen.route){

                        }
                        composable(NavigationRoute.FailureBookingScreen.route){

                        }
                        composable(NavigationRoute.EntryORScannerScreen.route){

                        }
                        composable(NavigationRoute.ExitORScannerScreen.route){

                        }
                        composable(NavigationRoute.SettingsScreen.route){

                        }
                    }
                }
            }
        }
    }
}

