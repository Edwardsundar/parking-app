package com.app.parker.presentation

import android.util.Log
import android.widget.ProgressBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.parker.R
import com.app.parker.presentation.nav.NavigationRoute
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    modifier: Modifier = Modifier,
    viewModel : MainViewModel,
    onContinueShoppingClick: () -> Unit
) {

    var paymentIsSuccess by remember {
        mutableStateOf(false)
    }

    var holderName by remember {
        mutableStateOf("")
    }

    var cardNumber by remember {
        mutableStateOf("")
    }

    var cvcNumber by remember {
        mutableStateOf("")
    }

    var expiryDate by remember {
        mutableStateOf("")
    }

    Scaffold(
        modifier = modifier.background(Color.White),
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "${viewModel.selectedPlace?.name}",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            })
        },
        containerColor = Color.White
    ) { paddingValues ->
        PaymentScreenContent(
            modifier = Modifier.padding(paddingValues),
            cardHolderName = holderName,
            cardNumber = cardNumber,
            cardExpiryDate = expiryDate,
            cvc = cvcNumber,
            onHolderNameChanged = {
                                  holderName = it
            },
            onCardNumberChanged = {
                                  cardNumber = it
            },
            onCvcChanged = {
                           cvcNumber = it
            },
            onExpiryDateChanged = {
                              expiryDate = it
            },
            totalAmount = viewModel.totalAmount.value,
            onCardInputClicked = {

            },
            rotated = false,
            onCardClick = {

            },
            onPaymentClicked = {
                paymentIsSuccess = !paymentIsSuccess
                viewModel.paymentIsSuccess()
            },
            isPaymentDone = paymentIsSuccess,
            isLoading = false,
            onContinueShoppingClick = onContinueShoppingClick
        )
    }
}

@Composable
private fun PaymentScreenContent(
    modifier: Modifier,
    cardHolderName: String,
    cardNumber: String,
    cardExpiryDate: String,
    cvc: String,
    onHolderNameChanged: (String) -> Unit,
    onCardNumberChanged: (String) -> Unit,
    onExpiryDateChanged: (String) -> Unit,
    onCvcChanged: (String) -> Unit,
    totalAmount: String,
    onCardInputClicked: (Boolean) -> Unit,
    rotated: Boolean,
    onCardClick: () -> Unit,
    onPaymentClicked: () -> Unit,
    isPaymentDone: Boolean,
    isLoading: Boolean,
    onContinueShoppingClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(Color.White)
    ) {
        if (isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else if (isPaymentDone) {
            val visible by remember {
                mutableStateOf(true)
            }
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + expandHorizontally(animationSpec = tween(durationMillis = 20000 , easing = LinearEasing)),
                exit = fadeOut() + shrinkHorizontally(animationSpec = tween(1000)),
            ) {
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        painter = painterResource(id = R.drawable.payment_success),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .padding(top = 8.dp),
                        text = "Payment Successfull",
                        textAlign = TextAlign.Center
                    )
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

                    ShoppingButton(
                        modifier = modifier.padding(top = 8.dp),
                        onClick = onContinueShoppingClick,
                        buttonText = "Continue Booking"
                    )
                }
            }
        } else {
            ShoppingCreditCard(
                cardHolderName = cardHolderName,
                cardNumber = cardNumber,
                cardExpiryDate = cardExpiryDate,
                cvc = cvc,
                rotated = rotated,
                onCardClick = onCardClick
            )
            CardDetails(
                onHolderNameChanged = onHolderNameChanged,
                onCardNumberChanged = onCardNumberChanged,
                onCvcChanged = onCvcChanged,
                onExpiryDateChanged = onExpiryDateChanged,
                onCardInputClicked = onCardInputClicked,
                cardHolderName = cardHolderName,
                cardExpiryDate = cardExpiryDate,
                cardNumber = cardNumber,
                cvc = cvc
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(96.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PaymentDetailItem(
                    title = "Service charge",
                    description = "20"
                )
                PaymentDetailItem(
                    title = "Total amount",
                    description = totalAmount
                )
            }
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                ShoppingButton(
                    onClick = onPaymentClicked,
                    buttonText = "Pay Now"
                )
            }
        }
    }
}

@Composable
fun CardDetails(
    onHolderNameChanged: (String) -> Unit,
    onCardNumberChanged: (String) -> Unit,
    onCvcChanged: (String) -> Unit,
    onExpiryDateChanged: (String) -> Unit,
    cardHolderName: String,
    cardNumber: String,
    cardExpiryDate: String,
    cvc: String,
    onCardInputClicked: (Boolean) -> Unit
) {
    CardHolderName(
        onHolderNameChanged = onHolderNameChanged,
        holderNameVal = cardHolderName,
        onCardInputClicked = onCardInputClicked
    )
    CardNumber(
        onCardNumberChanged = onCardNumberChanged,
        cardNumberVal = cardNumber,
        onCardInputClicked = onCardInputClicked
    )
    CardDateAndCVC(
        onExpiryDateChanged = onExpiryDateChanged,
        onCvcChanged = onCvcChanged,
        expiryDateVal = cardExpiryDate,
        cvcVal = cvc,
        onCardInputClicked = onCardInputClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardHolderName(
    onHolderNameChanged: (String) -> Unit,
    holderNameVal: String,
    onCardInputClicked: (Boolean) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        value = holderNameVal,
        onValueChange = onHolderNameChanged,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        label = {
            Text(text = "Card Holder" )
        },
        interactionSource = interactionSource(
            cardRotate = false,
            onCardInputClicked = onCardInputClicked
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardNumber(
    onCardNumberChanged: (String) -> Unit,
    cardNumberVal: String,
    onCardInputClicked: (Boolean) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        value = cardNumberVal,
        onValueChange = onCardNumberChanged,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = {
            Text(text = "Card number")
        },
        interactionSource = interactionSource(
            cardRotate = false,
            onCardInputClicked = onCardInputClicked
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardDateAndCVC(
    onCvcChanged: (String) -> Unit,
    onExpiryDateChanged: (String) -> Unit,
    cvcVal: String,
    expiryDateVal: String,
    onCardInputClicked: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            value = expiryDateVal,
            onValueChange = onExpiryDateChanged,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(text = "expiry date")
            },
            placeholder = {
                Text(text = "Enter your expiry date")
            },
            interactionSource = interactionSource(
                cardRotate = false,
                onCardInputClicked = onCardInputClicked
            )
        )
        Spacer(modifier = Modifier.width(32.dp))
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            value = cvcVal,
            onValueChange = onCvcChanged,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(text = "cvc")
            },
            interactionSource = interactionSource(
                cardRotate = true,
                onCardInputClicked = onCardInputClicked
            )
        )
    }
}

@Composable
private fun interactionSource(
    cardRotate: Boolean,
    onCardInputClicked: (Boolean) -> Unit
): MutableInteractionSource {
    return remember { MutableInteractionSource() }
        .also { interactionSource ->
            LaunchedEffect(interactionSource) {
                interactionSource.interactions.collect {
                    if (it is PressInteraction.Release) {
                        onCardInputClicked(cardRotate)
                    }
                }
            }
        }
}
