package com.example.tekirkotlin.view.cat_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tekirkotlin.R


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
                    .background(MaterialTheme.colors.secondary)) {

                }
            }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint : String = "",
    onSearch: (String) -> Unit = {}
) {

    
}

@Composable
fun CatList() {

}