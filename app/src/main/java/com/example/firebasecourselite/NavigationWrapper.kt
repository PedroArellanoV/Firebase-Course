package com.example.firebasecourselite

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firebasecourselite.presentation.home.HomeScreen
import com.example.firebasecourselite.presentation.initial.InitialScreen
import com.example.firebasecourselite.presentation.login.LoginScreen
import com.example.firebasecourselite.presentation.signup.SignupScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth,
    initial: String
) {

    NavHost(navController = navHostController, startDestination = initial) {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navHostController.navigate("login") },
                navigateToSignUp = { navHostController.navigate("signup") }
            )
        }
        composable("login") {
            LoginScreen(auth, navHostController, navigateHome = {
                navHostController.navigate("home")
            })
        }
        composable("signup") {
            SignupScreen(
                modifier = Modifier,
                auth = auth,
                navigateHome = {
                    navHostController.navigate("home")
                },
                navController = navHostController
            )
        }
        composable("home") {
            HomeScreen(auth = auth, navigateToInitial = {navHostController.navigate("initial")})
        }
    }
}