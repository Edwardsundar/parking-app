package com.app.parker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.parker.constant.CommonUtil
import com.app.parker.presentation.EntryORScannerScreen
import com.app.parker.presentation.FailureBookin
import com.app.parker.presentation.LoginScreen
import com.app.parker.presentation.MainViewModel
import com.app.parker.presentation.ParkingAreaList
import com.app.parker.presentation.ParkingAreaTopView
import com.app.parker.presentation.PaymentScreen
import com.app.parker.presentation.SignUpScreen
import com.app.parker.presentation.SuccessBookin
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
                        if (CommonUtil.isLoggedIn(this)) NavigationRoute.ParkingAreaListingScreen.route
                        else NavigationRoute.LoginScreen.route
                    ){
                        composable(route = NavigationRoute.LoginScreen.route){
                            LoginScreen(
                                navController ,
                                viewModel
                                )
                        }
                        composable(route =NavigationRoute.SignupScreen.route){
                            SignUpScreen(navController , viewModel)
                            if (viewModel.loginSuccess.value.isNotEmpty()){
                                showToast(viewModel.loginSuccess.value)
                                viewModel.loginSuccess.value = ""
                            }
                        }
                        composable(NavigationRoute.ParkingAreaListingScreen.route){
                            ParkingAreaList(navController, viewModel )
                            if (viewModel.loginSuccess.value.isNotEmpty()){
                                showToast(viewModel.loginSuccess.value)
                                viewModel.loginSuccess.value = ""
                            }
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
                                navController.navigate(
                                    route = NavigationRoute.ParkingAreaListingScreen.route,
                                    builder = {
                                        popUpTo(NavigationRoute.ParkingAreaListingScreen.route) {
                                            inclusive = true // Set to true to also remove the current screen from the back stack
                                        }
                                    }
                                )
                            }
                        }
                        composable(NavigationRoute.SuccessFullBookingScreen.route){
                            SuccessBookin(navController = navController)
                        }
                        composable(NavigationRoute.FailureBookingScreen.route){
                            FailureBookin(navController = navController)
                        }
                        composable(NavigationRoute.EntryQRScannerScreen.route){
                            EntryORScannerScreen(
                                navController = navController,
                                viewModel = viewModel,
                                isEnter = true
                            )
                        }
                        composable(NavigationRoute.ExitORScannerScreen.route){
                            EntryORScannerScreen(
                                navController = navController,
                                viewModel = viewModel,
                                isEnter = false
                            )
                        }
                        composable(NavigationRoute.SettingsScreen.route){

                        }
                    }
                }
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

