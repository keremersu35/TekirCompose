package com.example.tekirkotlin.view.cat_list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tekirkotlin.R
import com.example.tekirkotlin.view.cat_list.components.*

@Composable
fun CatListScreen(navController: NavController, viewModel: CatListViewModel = hiltViewModel()) {
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
                    .align(CenterHorizontally)
                    .padding(5.dp))

            Text(text = "Tekir", style = typography.h1, modifier = Modifier
                .wrapContentHeight()
                .align(CenterHorizontally))

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

                CatList(navController = navController)
            }
        }
    }
}