package com.example.tekirkotlin.view.cat_list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tekirkotlin.R
import com.example.tekirkotlin.view.components.CatList
import com.example.tekirkotlin.view.components.SearchBar

@Composable
fun CatListScreen(navController: NavController, viewModel: CatListViewModel = hiltViewModel()) {
    Surface(color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)) {

            Row(modifier = Modifier.fillMaxWidth()){
                Spacer(modifier = Modifier.weight(0.3f))
                Column(modifier = Modifier
                    .align(CenterVertically)
                    .weight(0.3f)){
                    Image(
                        painterResource(R.drawable.cat_dashboard),
                        "Cat",
                        modifier = Modifier
                            .height(60.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(5.dp))

                    Text(text = "Tekir", style = MaterialTheme.typography.h1, modifier = Modifier
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally))
                }

                Icon(
                    Icons.Filled.Favorite, contentDescription = "", tint = Color.Red,
                    modifier = Modifier
                        .size(50.dp)
                        .align(CenterVertically)
                        .padding(5.dp)
                        .weight(0.3f).clickable( onClick = {navController.navigate("cat_fav_list_screen")  }))
            }

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