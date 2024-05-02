package com.app.parker.presentation

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.app.parker.R
import com.app.parker.presentation.nav.NavigationRoute
import com.app.parker.presentation.qr.QRCodeScannerScreen
import com.gowtham.ratingbar.RatingBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryORScannerScreen(
    navController: NavController,
    viewModel: MainViewModel,
    isEnter : Boolean
){
    var result by remember {
        mutableStateOf("")
    }
    val isSuccessToOpen by viewModel.isSuccessToOpen.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Column {
                    Text(
                        text = "${viewModel.selectedPlace?.name}",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "${viewModel.selectedPlace?.address}",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            })
        }
    ) {paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            QRCodeScannerScreen(urlText = result) {
                if (it.contains("open" , true) && isEnter && result.isEmpty()){
                    result = it
                    viewModel.openGate(isEnter)
                } else if (it.contains("close" , true) && !isEnter && result.isEmpty()){
                    viewModel.openGate(isEnter)
                    navController.navigate(NavigationRoute.PaymentScreen.route)
                }
            }
        }
        if (isSuccessToOpen == true){
            navController.navigate(
                route = if (isEnter) NavigationRoute.SuccessFullBookingScreen.route else NavigationRoute.PaymentScreen.route,
                builder = {
                    val currentScreen = if (isEnter) NavigationRoute.EntryQRScannerScreen.route else NavigationRoute.ExitORScannerScreen.route
                    popUpTo(currentScreen) {
                        inclusive = true // Set to true to also remove the current screen from the back stack
                    }
                }
            )
            viewModel.setSuccessToOpenNull()
        }
        if (isSuccessToOpen == false){
            navController.navigate(
                route = if (isEnter) NavigationRoute.FailureBookingScreen.route else NavigationRoute.PaymentScreen.route,
                builder = {
                    val currentScreen = if (isEnter) NavigationRoute.EntryQRScannerScreen.route else NavigationRoute.ExitORScannerScreen.route
                    popUpTo(currentScreen) {
                        inclusive = true // Set to true to also remove the current screen from the back stack
                    }
                }
            )
            viewModel.setSuccessToOpenNull()
        }
    }
}


@Composable
fun ComposeLottieAnimation(modifier: Modifier, isSuccess: Boolean, isFailed: Boolean) {

    val float by animateFloatAsState(targetValue = 1f)

    val clipSpecs = LottieClipSpec.Progress(
        min = if (isFailed) 0.499f else 0.0f,
        max = if (isSuccess) 0.44f else if (isFailed) 0.95f else 0.282f
    )

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_success))

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = if (isSuccess || isFailed) 1 else LottieConstants.IterateForever,
        clipSpec = clipSpecs,
    )
}



















