package dev.xnikai.drink_it

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.xnikai.drink_it.components.login.CreateUserPage
import dev.xnikai.drink_it.ui.theme.primaryFont

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val buttonColors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00b4d8)
            )
            if (viewModel.userState.userEntity == null) {
                CreateUserPage(viewModel = viewModel)
            } else {
                MainScreen(viewModel)
            }
            if (viewModel.alertDialogState.isShow) {
                var needToDrinkState by remember {
                    mutableStateOf(viewModel.userState.userEntity!!.needToDrink)
                }
                AlertDialog(
                    icon = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = viewModel.alertDialogState.icon,
                                contentDescription = "Icon"
                            )
                            Text(text = viewModel.alertDialogState.title)
                        }
                    },
                    text = {
                        if (viewModel.alertDialogState.isEditDialog) {
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(text = "Need to drink: $needToDrinkState")
                                Row {
                                    IconButton(
                                        onClick = {
                                            if(needToDrinkState >= 1) {
                                                needToDrinkState++
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Add,
                                            contentDescription = "Add Icon"
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            if(needToDrinkState >= 1) {
                                                needToDrinkState--
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Remove,
                                            contentDescription = "Remove Icon"
                                        )
                                    }
                                }
                            }
                        } else {
                            Text(text = viewModel.alertDialogState.text)
                        }
                    },
                    onDismissRequest = { viewModel.alertDialogState.onDismiss() },
                    confirmButton = {
                        if (viewModel.alertDialogState.confirmButtonText != null) {
                            Button(
                                onClick = { viewModel.alertDialogState.onConfirm(needToDrinkState)},
                                colors = buttonColors
                            ) {
                                Text(text = viewModel.alertDialogState.confirmButtonText!!)
                            }
                        }
                    },
                    dismissButton = {
                        if (viewModel.alertDialogState.dismissButtonText != null) {
                            OutlinedButton(onClick = { viewModel.alertDialogState.onDismiss() }) {
                                Text(text = viewModel.alertDialogState.confirmButtonText!!)
                            }
                        }
                    },
                    containerColor = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFf4faff))
            .padding(15.dp)
    ) {
        Row(
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Hello, ${viewModel.userState.userEntity?.name}!",
                fontSize = 18.sp,
                fontFamily = primaryFont,
                fontWeight = FontWeight.SemiBold
            )
        }
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val isPressed = remember {
                mutableStateOf(false)
            }
            val scaleAnimate = animateFloatAsState(if (isPressed.value) 0.8f else 1f, label = "")
            Image(
                painter = painterResource(id = R.drawable.glass_icon),
                contentDescription = "Glass Icon",
                modifier = Modifier
                    .scale(scaleAnimate.value)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_UP -> {
                                isPressed.value = false
                                viewModel.addGlass()
                            }

                            MotionEvent.ACTION_DOWN -> {
                                isPressed.value = true
                            }

                            else -> {}
                        }
                        true
                    }
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Need to drink: ${viewModel.userState.userEntity?.drinkCount}/${viewModel.userState.userEntity?.needToDrink} glasses",
                fontSize = 18.sp,
                fontFamily = primaryFont,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "(Click on glass, if your drink a water)",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val buttonColors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00b4d8)
                )
                Row {
                    Button(
                        onClick = {
                            viewModel.alertDialogState =
                                viewModel.alertDialogState.copy(isShow = true,
                                    confirmButtonText = "Apply",
                                    onConfirm = {
                                        viewModel.addNeedToDrink(it)
                                        viewModel.alertDialogState = viewModel.alertDialogState.copy(
                                            isShow = false
                                        )
                                    },
                                    onDismiss = {
                                        viewModel.alertDialogState =
                                            viewModel.alertDialogState.copy(
                                                isShow = false
                                            )
                                    })
                        },
                        colors = buttonColors
                    ) {
                        Icon(imageVector = Icons.Rounded.Edit, contentDescription = "Edit Icon")
                        Text(text = "Edit")
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(onClick = { viewModel.resetDrinkCount() }, colors = buttonColors) {
                        Icon(imageVector = Icons.Rounded.Refresh, contentDescription = "Edit Icon")
                        Text(text = "Reset")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewStartScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFf4faff)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            Text(
                text = "Hello, User! " +
                        "\nPlease, say your name! ",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color(0xFF485665),
                fontFamily = primaryFont
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            shape = CircleShape,
            leadingIcon = {
                Icon(imageVector = Icons.Rounded.Person, contentDescription = "Person Icon")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(0xFF0e6ba8),
                unfocusedLeadingIconColor = Color(0xFF0e6ba8)
            ),
            placeholder = {
                Text(
                    text = "Print your name",
                    fontFamily = primaryFont,
                    fontWeight = FontWeight.SemiBold
                )
            }
        )
        TextButton(
            onClick = {
                // viewModel.changeLogin()
            },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF0e6ba8),
//                containerColor = Color.Transparent
            )
        ) {
            Text(text = "Continue", fontFamily = primaryFont, fontWeight = FontWeight.SemiBold)
            Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = "Arrow Icon")
        }
    }
}