package com.kuluruvineeth.composefacebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kuluruvineeth.composefacebook.ui.theme.ComposeFacebookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFacebookTheme {
                TransparentSystemBars()

                val navController = rememberNavController()
                val homeRoute = "home"
                val signinRoute = "signin"
                NavHost(navController = navController, startDestination = "home"){
                    composable("home"){
                        HomeScreen(
                            navigateToSignIn = {
                                navController.navigate(signinRoute){
                                    popUpTo(homeRoute){
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                    composable(signinRoute){
                        SignInScreen(
                            navigateToHome = {
                                navController.navigate(homeRoute){
                                    popUpTo(signinRoute){
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun TransparentSystemBars() {
    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )

        // setStatusBarColor() and setNavigationBarColor() also exist

        onDispose {}
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeFacebookTheme {
        
    }
}