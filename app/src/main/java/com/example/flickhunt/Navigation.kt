package com.example.flickhunt

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flickhunt.utils.Constants

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MovieListScreen.route){
        composable(route = Screens.MovieListScreen.route){
            MainActivity()
        }
        composable(
            route = Screens.MovieDetailsScreen.route + "/{movie_id}",
            arguments = listOf(
                navArgument("movie_id"){
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ){ entry ->}
    }
}

sealed class Screens(val route : String){
    object MovieListScreen : Screens(Constants.MOVIE_SCREEN)
    object MovieDetailsScreen : Screens(Constants.MOVIE_DETAILS_SCREEN)

    fun withArgs(vararg args : String) : String{
        return buildString {
            append(route)
            args.forEach { append("/$args") }
        }
    }
}
