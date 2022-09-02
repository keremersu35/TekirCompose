package com.example.tekirkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tekirkotlin.view.cat_detail.CatDetailScreen
import com.example.tekirkotlin.view.cat_list.CatListScreen
import com.example.tekirkotlin.ui.theme.TekirKotlinTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TekirKotlinTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "cat_list_screen"){

                    composable(route = "cat_list_screen"){
                        CatListScreen(navController = navController)
                    }
                    composable(route = "cat_detail_screen/{breedId}", arguments = listOf(
                       navArgument("breedId"){
                           type = NavType.StringType
                       }
                    )) {
                        val breedId = remember {
                            it.arguments?.getString("breedId")
                        }
                        CatDetailScreen(id = breedId?: "", navController = navController)
                    }
                }
            }
        }
    }
}

