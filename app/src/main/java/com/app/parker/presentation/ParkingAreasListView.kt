package com.app.parker.presentation


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.app.parker.presentation.nav.NavigationRoute

data class ParkingArea(val name: String, val imageUrl: String, val address: String)




@Composable
fun ParkingAreaList(
    navController: NavController,
    viewModel: MainViewModel
) {
    val areaLists = viewModel.listingAreas.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        val searchQuery = remember { mutableStateOf("") }
        SearchBar(searchQuery)

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 8.dp,
            state = rememberLazyStaggeredGridState()
        ){
            items(areaLists.value.size){index ->
                ParkingAreaItem(areaLists.value[index]) {
                    viewModel.selectedPlace = areaLists.value[index]
                    navController.navigate(NavigationRoute.VehicleSelectionScreen.route )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun ParkingAreaItem(
    parkingArea: ParkingArea,
    onItemClick : () -> Unit
) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .clickable {
                onItemClick()
            }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = parkingArea.name,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            AsyncImage(
                model = parkingArea.imageUrl,
                contentDescription = parkingArea.address,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(
                text = parkingArea.address,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun SearchBar(
    searchQuery: MutableState<String>,
    viewModel: MainViewModel = hiltViewModel<MainViewModel>()
) {
    OutlinedTextField(
        value = searchQuery.value,
        onValueChange = {
            searchQuery.value = it
            viewModel.searchAreas(it)
                        },
        label = { Text("Search") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {}
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun LoadImage(url: String, contentDescription: String): Painter {
    val painter = rememberImagePainter(url)
    return painter
}
