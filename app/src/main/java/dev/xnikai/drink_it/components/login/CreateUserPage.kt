package dev.xnikai.drink_it.components.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.xnikai.drink_it.MainViewModel

@Composable
fun CreateUserPage(
    viewModel: MainViewModel
) {
    val createUserState = remember {
        mutableStateOf(1)
    }
    when(createUserState.value) {
        1 -> {
            CreateName(viewModel = viewModel)
        }
    }
}

@Composable
fun CreateName(
    viewModel: MainViewModel
) {
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
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = Color(0xFF485665)
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = viewModel.inputNameState,
            onValueChange = {
                viewModel.changeName(it)
            },
            shape = CircleShape,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Person Icon"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF0e6ba8),
                unfocusedLeadingIconColor = Color(0xFF0e6ba8),
            ),
            placeholder = {
                Text(text = "Print your name")
            }
        )
        TextButton(
            onClick = {
                viewModel.changeLogin()
            },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF0e6ba8),
            )
        ) {
            Text(text = "Continue")
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = "Arrow Icon"
            )
        }
    }
}