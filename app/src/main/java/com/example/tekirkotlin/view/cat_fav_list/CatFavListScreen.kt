package com.example.tekirkotlin.view.cat_fav_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tekirkotlin.R
import com.example.tekirkotlin.view.components.CatFavList
import com.example.tekirkotlin.view.components.CatList
import com.example.tekirkotlin.view.components.SearchBar

@Composable
fun CatFavListScreen(navController: NavController, viewModel: CatFavListViewModel = hiltViewModel()) {
    Surface(color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)) {

            Image(
                painterResource(R.drawable.cat_dashboard),
                "Cat",
                modifier = Modifier
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp))

            Text(text = "Favorites", style = MaterialTheme.typography.h1, modifier = Modifier
                .wrapContentHeight()
                .align(Alignment.CenterHorizontally))

            Spacer(modifier = Modifier.height(5.dp))

            Column( modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colors.secondary)) {

                SearchBar(hint = "Search ...", modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                ){
                    viewModel.searchCatList(it)
                }

                CatFavList(navController = navController)
            }
        }
    }
}