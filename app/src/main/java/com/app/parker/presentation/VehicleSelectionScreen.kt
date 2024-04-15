package com.app.parker.presentation

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.parker.R
import com.app.parker.constant.CommonUtil
import com.app.parker.constant.VehicleType
import com.app.parker.presentation.nav.NavigationRoute
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VehicleSelectionScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.NOON)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Three Options") }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {padding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OptionBox(imgId = R.drawable.img_bike){
                viewModel.selectedVehicle = VehicleType.Bike
                dateDialogState.show()
            }
            Spacer(modifier = Modifier.height(16.dp))
            OptionBox(imgId = R.drawable.img_car){
                viewModel.selectedVehicle = VehicleType.Car
                dateDialogState.show()
            }
            Spacer(modifier = Modifier.height(16.dp))
            OptionBox(imgId = R.drawable.img_bus){
                viewModel.selectedVehicle = VehicleType.Van
                dateDialogState.show()
            }
        }
    }

    MaterialDialog(
        dialogState = dateDialogState,
        onCloseRequest = {
            Toast.makeText(
                CommonUtil.appContext,
                "Please Select the date and time to book your slot",
                Toast.LENGTH_LONG
            ).show()
        },
        buttons = {
            positiveButton(
                text = "Select"
            ){
                viewModel.selectedDate = formattedDate
                timeDialogState.show()
            }
            negativeButton(
                text = "Cancel"
            )
        }
    ) {
        this.datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a Date to Book",
            allowedDateValidator = {
                it.month == LocalDate.now().month &&
                        it.year == LocalDate.now().year &&
                        it.dayOfMonth > LocalDate.now().dayOfMonth
            }
        ){
            pickedDate = it
        }
    }

    MaterialDialog(
        dialogState = timeDialogState,
        onCloseRequest = {
            Toast.makeText(
                CommonUtil.appContext,
                "Please Select the date and time to book your slot",
                Toast.LENGTH_LONG
            ).show()
        },
        buttons = {
            positiveButton(
                text = "Select"
            ){
                viewModel.selectedTime = formattedTime
                navController.navigate(NavigationRoute.ParkingAreaTopView.route)
            }
            negativeButton(
                text = "Cancel"
            )
        }
    ) {
        this.timepicker(
            initialTime = LocalTime.MIDNIGHT,
            title = "Pick a Time to Book",
        ){
            pickedTime = it
        }
    }
}

@Composable
fun OptionBox(imgId: Int, count: Int = 10 , total : Int = 10 , onClick : () -> Unit) {
    androidx.compose.material.Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .clickable {
                onClick()
            },
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(imgId),
                contentDescription = null ,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxSize(1f)
            )
        }
        Row(
            horizontalArrangement =Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){
            Text(
                text = "Avilable $count"
            )
            Text(
                text = "Total $count"
            )
        }

    }
}
