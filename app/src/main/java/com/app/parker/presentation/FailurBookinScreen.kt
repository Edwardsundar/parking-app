package com.app.parker.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.parker.presentation.nav.NavigationRoute
import com.gowtham.ratingbar.RatingBar

@Composable
fun FailureBookin(
    navController : NavController
){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
    ){
        ComposeLottieAnimation(
            modifier = Modifier.align(alignment = Alignment.Center),
            isSuccess = false,
            isFailed = true
        )

        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            var rating: Float by remember { mutableStateOf(3.2f) }

            RatingBar(
                value = rating,
                onValueChange = {
                    rating = it
                },
                onRatingChanged = {
                    Log.d("TAG", "onRatingChanged: $it")
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 45.dp),
                onClick = {
                    navController.navigate(
                        route = NavigationRoute.ParkingAreaListingScreen.route,
                        builder = {
                            popUpTo(NavigationRoute.ParkingAreaListingScreen.route) {
                                inclusive = true // Set to true to also remove the current screen from the back stack
                            }
                        }
                    )
                }
            ) {
                Text(
                    text = "Continue"
                )
            }
        }
    }
}