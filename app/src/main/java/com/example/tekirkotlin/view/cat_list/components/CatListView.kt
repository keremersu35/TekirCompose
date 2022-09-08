package com.example.tekirkotlin.view.cat_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.tekirkotlin.R
import com.example.tekirkotlin.model.Cat
import com.example.tekirkotlin.utils.FavListManager
import com.example.tekirkotlin.view.cat_list.CatListViewModel

@Composable
fun CatList(navController: NavController,
            viewModel: CatListViewModel = hiltViewModel()
) {

    val catList by remember {viewModel.catList}
    val errorMessage by remember {viewModel.errorMessage}
    val isLoading by remember {viewModel.isLoading}

    CatListView(catList, navController)

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))

    Box( modifier = Modifier.fillMaxSize()){
        if(isLoading){
            Box(modifier = Modifier
                .width(120.dp)
                .height(120.dp)
                .align(Alignment.Center)) {
                Lottie()
            }

            //CircularProgressIndicator(modifier = Modifier.align(Center), color = Color.Blue)
        }
        if(errorMessage.isNotEmpty()){
            RetryView(error = errorMessage) {
                viewModel.loadCats()
            }
        }
    }
}

@Composable
fun CatListView(cats: List<Cat>, navController: NavController) {

    val context = LocalContext.current
    val favListManager = FavListManager(context)

    LazyVerticalGrid(columns = GridCells.Fixed(2),){
        items(cats){ cat ->
            if(favListManager.checkBreed(breedId = cat.id)){
                CatRow(navController = navController, cat = cat, true)
            }else{
                CatRow(navController = navController, cat = cat, false)
            }

        }
    }
}