package com.example.firebasecourselite.presentation.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebasecourselite.R
import com.example.firebasecourselite.ui.theme.Black
import com.example.firebasecourselite.ui.theme.Gray
import com.example.firebasecourselite.ui.theme.Green

@Composable
fun InitialScreen(navigateToSignUp: () -> Unit, navigateToLogin: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Gray, Black), startY = 0f, endY = 600f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.spotify),
            contentDescription = "",
            modifier = Modifier.clip(CircleShape)
        )
        Spacer(modifier = Modifier.weight(0.5f))
        Text(
            "Millions of songs.",
            color = Color.White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Free on Spotify.",
            color = Color.White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(0.5f))
        Button(
            onClick = { navigateToSignUp() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green)
        ) {
            Text("Sing up free.", color = Black, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))
        CustomButton(
            onClick = {},
            image = R.drawable.google,
            text = "Continue with Google."
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomButton(
            onClick = {},
            image = R.drawable.facebook,
            text = "Continue with Facebook."
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Login",
            color = Color.White,
            modifier = Modifier
                .clickable { navigateToLogin() },
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun CustomButton(onClick: () -> Unit, image: Int, text: String) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Black)
    ) {
        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )
            Text(
                text,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}