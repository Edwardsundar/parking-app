package com.app.parker.presentation

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.app.parker.R
import com.app.parker.constant.CommonUtil
import com.app.parker.constant.VehicleType
import com.app.parker.presentation.nav.NavigationRoute
import com.app.parker.ui.theme.LightGreen
import com.gowtham.ratingbar.RatingBar


@Composable
fun ParkingAreaTopView(
    navController: NavController,
    viewModel : MainViewModel
){
    val selectedDate = "11-Jan-2024"
    val selectedTime = "9:50"
    var selectedSlot by remember {
        mutableStateOf("")
    }
    var selectedSlotBool by remember {
        mutableStateOf(false)
    }

    val allData by viewModel.availablePlace.collectAsState()

    val bookResult by viewModel.bookingResult.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = {

                Text(
                    text = "${viewModel.selectedPlace?.name}",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = Color.White
                )
            })
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            CardElevation(
                id = "A0",
                alreadyBooked = allData?.v0 ?: false
            ){
                selectedSlot = "A0"
                selectedSlotBool = true
            }
            Spacer(modifier = Modifier.height(20.dp))
            CardElevation(
                id = "A1",
                alreadyBooked = allData?.v1 ?: false
            ){
                selectedSlot = "A1"
                selectedSlotBool = true
            }
        }
    }

    if (bookResult == true ){
        navController.navigate(NavigationRoute.SuccessFullBookingScreen.route)
        viewModel.closeBookingPlace()
        viewModel.setBookResultNull()
    }
    if (bookResult == false ){
        navController.navigate(NavigationRoute.FailureBookingScreen.route)
        viewModel.closeBookingPlace()
        viewModel.setBookResultNull()
    }

    if (selectedSlotBool){
        AlertDialogWithContent(
            title = "Confirm the slot for further process",
            content = "Selected Slot is $selectedSlot plese make sure to on the time to the spot to avoid any aditional penalty verfy the selected time and the date here date ${selectedDate} time ${selectedTime}",
            drawableId = CommonUtil.vehicleTypeToDrawable(VehicleType.Bike),
            onCancel = {
                selectedSlot = ""
                selectedSlotBool = false
            },
            onConfirm = {
                viewModel.selectedParkingSlot = selectedSlot
                selectedSlot = ""
                selectedSlotBool = false
                viewModel.bookNewSlot()
            }
        )
    }
}

@Composable
fun CardElevation(
    id:String,
    alreadyBooked : Boolean,
    onItemClick: (id: String) -> Unit
) {
    var rating: Float by remember { mutableStateOf(3.2f) }
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if(alreadyBooked) Color.Red else Color(0xFFDAE1E7),
        modifier = Modifier
            .height(210.dp)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.wrapContentSize(),
                    color = Color(0xFFD1D5E1)
                ) {
                    Text(
                        text = if (alreadyBooked) "Booked" else "Open",
                        fontSize =  12.sp,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = id,
                    fontSize =  24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(text = "Price Per Hr : 50" , color = Color.Black)

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = rating.toString(),
                        fontSize =  14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    RatingBar(
                        value = rating,
                        onValueChange = {
                            rating = it
                        },
                        onRatingChanged = {
                            Log.d("TAG", "onRatingChanged: $it")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedButton(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                    ),
                    onClick = { onItemClick(id) }
                ) {
                    Text(
                        text = "Book Noew",
                        fontSize =  11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(width = 150.dp, height = 140.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.img_car),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
        }
    }
}

