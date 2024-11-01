package com.example.firebasecourselite.presentation.signup

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebasecourselite.R
import com.example.firebasecourselite.ui.theme.Black
import com.example.firebasecourselite.ui.theme.SelectedField
import com.example.firebasecourselite.ui.theme.ShapeButton
import com.example.firebasecourselite.ui.theme.UnselectedField
import com.google.firebase.auth.FirebaseAuth


@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    auth: FirebaseAuth,
    navigateHome: () -> Unit,
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Black
            )
            .padding(14.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton({
                navController.navigateUp()
            }) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    tint = Color.White,
                    contentDescription = ""
                )
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "Email or username",
            fontSize = 40.sp,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
            maxLines = 1,
            value = email,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField, focusedContainerColor = SelectedField,
                unfocusedBorderColor = Color.Black, focusedBorderColor = Color.Black
            ),
            onValueChange = { email = it })
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Password",
            fontSize = 40.sp,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold
        )
        OutlinedTextField(
            maxLines = 1,
            value = password,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { password = it },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField, focusedContainerColor = SelectedField,
                unfocusedBorderColor = Color.Black, focusedBorderColor = Color.Black
            ),
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                if (passwordVisible) {
                    IconButton(onClick = { passwordVisible = false }) {
                        Icon(
                            painter = painterResource(R.drawable.eye_line),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                } else {
                    IconButton(onClick = { passwordVisible = true }) {
                        Icon(
                            painter = painterResource(R.drawable.eye_fill),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            }
        )
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            //Registrado
                            Log.i("aris", "Registro OK")
                            navigateHome()
                        } else {
                            //Error
                            Log.i("aris", "Registro KO")
                        }
                    }
                },
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(ShapeButton)
            ) {
                Text(text = "Sign Up")
            }
        }

    }
}