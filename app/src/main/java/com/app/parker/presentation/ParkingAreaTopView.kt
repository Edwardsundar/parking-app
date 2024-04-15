package com.app.parker.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.parker.R
import com.app.parker.constant.CommonUtil
import com.app.parker.constant.VehicleType
import com.app.parker.presentation.nav.NavigationRoute
import com.app.parker.ui.theme.LightGreen

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            TopAppBar(title = {

                Text(
                    text = "${viewModel.selectedPlace?.name}",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            })
        }
    ) {

        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = Modifier
                .padding(it)
        ) {
            items(25){slotNumber->
                SingleParkingSpace(
                    slotNumber = slotNumber,
                    isBooked = true,
                    vehicleType = VehicleType.Van
                ){
                    selectedSlot = it
                }
            }
        }
    }

    if (selectedSlot.isNotEmpty()){
        AlertDialogWithContent(
            title = "Confirm the slot for further process",
            content = "Selected Slot is $selectedSlot plese make sure to on the time to the spot to avoid any aditional penalty verfy the selected time and the date here date ${selectedDate} time ${selectedTime}",
            drawableId = CommonUtil.vehicleTypeToDrawable(VehicleType.Bike),
            onCancel = {
                       selectedSlot = ""
            },
            onConfirm = {
                viewModel.selectedParkingSlot = selectedSlot
                selectedSlot = ""
                navController.navigate(NavigationRoute.PaymentScreen.route)
            }
        )
    }


}

@Composable
fun SingleParkingSpace(
    slotNumber : Int,
    isBooked : Boolean,
    vehicleType : VehicleType,
    onItemClick : (id : String)-> Unit
){
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .padding(6.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(5.dp),
                color = Color.Black
            )
            .clickable {
                onItemClick("A$slotNumber")
            },
        contentColor = if (isBooked) LightGreen else Color.Transparent
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Text(
                text = "A$slotNumber",
                fontSize = 50.sp
            )
            Image(
                painter = painterResource(id =CommonUtil.vehicleTypeToDrawable(vehicleType = vehicleType)),
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )
        }
    }
}

@Composable
fun CanvasLine(
    rowCount :Int,
    columnCount: Int
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val startX = 200f
        val endX = center.x + center.x - 200f
        val topY = 200f
        val bottomY = center.y+center.y-200f
        val centerLineLengthY = topY + bottomY
        val centerLineLengthX = startX + endX
        drawLine(
            start = Offset(center.x , topY),
            end = Offset(center.x ,bottomY ),
            color = Color.Black,
            strokeWidth = 5f
        )

        val spacePerCellY = centerLineLengthY / rowCount
        var tempTopY = topY
        (1..rowCount).forEach { row->
            drawLine(
                start = Offset(startX , tempTopY),
                end = Offset(endX ,tempTopY ),
                color = Color.Black,
                strokeWidth = 5f
            )
            tempTopY += spacePerCellY
        }

    }
}

@Preview
@Composable
fun CanvasLinePreview(){
    CanvasLine(rowCount = 5, columnCount = 2)
}