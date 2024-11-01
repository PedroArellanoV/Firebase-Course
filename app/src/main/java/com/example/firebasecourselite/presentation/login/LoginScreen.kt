package com.example.firebasecourselite.presentation.login

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.firebasecourselite.R
import com.example.firebasecourselite.ui.theme.Black
import com.example.firebasecourselite.ui.theme.SelectedField
import com.example.firebasecourselite.ui.theme.ShapeButton
import com.example.firebasecourselite.ui.theme.UnselectedField
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(auth: FirebaseAuth, navController: NavHostController, navigateHome: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(14.dp)
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
        OutlinedTextField(maxLines = 1,
            value = email,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField,
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color.Black
            ),
            onValueChange = { email = it })
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Password",
            fontSize = 40.sp,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold
        )
        OutlinedTextField(maxLines = 1,
            value = password,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { password = it },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField,
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color.Black
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
            })
        Spacer(Modifier.height(32.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    validateAuth(auth, email, password, navigateHome)
                },
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(ShapeButton)
            ) {
                Text(text = "Log in", color = SelectedField)
            }

            Spacer(Modifier.height(24.dp))

            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .padding(0.dp)
                    .height(32.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
            ) {
                Text(
                    "Log in without password",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 10.sp,
                    letterSpacing = 1.3.sp,
                    modifier = Modifier.padding(0.dp)
                )
            }
        }

    }
}

fun validateAuth(auth: FirebaseAuth, email: String, password: String, navigateHome: () -> Unit) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navigateHome()
            } else {
                Log.i("pepe", "ERROR LOG IN")
            }
        }
}
