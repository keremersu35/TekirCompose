package com.example.tekirkotlin.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tekirkotlin.model.Cat
import com.example.tekirkotlin.view.cat_fav_list.CatFavListViewModel
import com.example.tekirkotlin.view.cat_list.CatListViewModel

@Composable
fun CatFavList(navController: NavController,
            viewModel: CatFavListViewModel = hiltViewModel()
) {

    var catList by remember {viewModel.getCats()}
    val errorMessage by remember {viewModel.errorMessage}
    val isLoading by remember {viewModel.isLoading}
    var isFav = remember{ mutableStateOf(true)}

    CatFavListView(catList, navController, isFav)

    Box( modifier = Modifier.fillMaxSize()){
        if(isLoading){
            Box(modifier = Modifier
                .width(120.dp)
                .height(120.dp)
                .align(Alignment.Center)) {
                Lottie()
            }

        }
        if(errorMessage.isNotEmpty()){
            RetryView(error = errorMessage) {
                catList = viewModel.getCats().value
            }
        }

        if(!isFav.value){
            catList = viewModel.getCats().value
        }
    }
}

@Composable
fun CatFavListView(cats: List<Cat>, navController: NavController, isFav: MutableState<Boolean>) {

    LazyVerticalGrid(columns = GridCells.Fixed(2),){
        items(cats){ cat ->
            CatRow(navController = navController, cat = cat, isFav)
        }
    }
}