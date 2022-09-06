package com.example.tekirkotlin.view.cat_list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tekirkotlin.R
import com.example.tekirkotlin.model.Cat
import com.example.tekirkotlin.utils.checkName

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

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint : String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember{
        mutableStateOf("")
    }

    var isHintDisplayed by remember{
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier){
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),

            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                },
        )

        if(isHintDisplayed){
            Text(text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp))
        }
    }
}

@Composable
fun CatRow(navController: NavController, cat: Cat) {
    Box() {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .align(TopEnd)
                .zIndex(1f)
                .clip(CircleShape)
                .background(Color.White)
        ) {

            Icon(
                Icons.Filled.Favorite, "",
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.Center),
                tint = Color.Gray
            )
        }
        Column(modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(15.dp))
            .height(250.dp)
            .background(color = Color.White)
            .clickable {
                navController.navigate(
                    "cat_detail_screen/${cat.id}"
                )
            }) {

            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                if (cat.image?.url == null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.cat)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.loading),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(RoundedCornerShape(15.dp))
                    )
                } else {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(cat.image?.url)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.loading),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(RoundedCornerShape(15.dp))
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {

                Text(
                    text = cat.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp)
                        .align(CenterHorizontally)
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.marker),
                        contentDescription = "",
                        modifier = Modifier
                            .width(15.dp)
                            .height(15.dp),
                        tint = Color.Gray
                    )

                    Text(
                        text = cat.origin.checkName(cat.origin.toString()),
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.life_span),
                        contentDescription = "",
                        modifier = Modifier
                            .width(15.dp)
                            .height(15.dp),
                        tint = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(3.dp))

                    Text(
                        text = cat.lifeSpan.toString(),
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
fun CatList(navController: NavController,
            viewModel: CatListViewModel = hiltViewModel()) {

    val catList = remember {viewModel.catList}
    val errorMessage = remember {viewModel.errorMessage}
    val isLoading = remember {viewModel.isLoading}

    CatListView(catList.value, navController)

    Box( modifier = Modifier.fillMaxSize()){
        if(isLoading.value){
            CircularProgressIndicator(modifier = Modifier.align(Center), color = Color.Blue)
        }
        if(errorMessage.value.isNotEmpty()){
            RetryView(error = errorMessage.value) {
                viewModel.loadCats()
            }
        }
    }

}

@Composable
fun CatListView(cats: List<Cat>, navController: NavController) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),){
        items(cats){ cat ->
            CatRow(navController = navController, cat = cat)
        }
    }
}

@Composable
fun RetryView(
    error: String,
    onRetry: () -> Unit
) {
    Column() {
        Text(text = error, color = Color.Red, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { onRetry },
        modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Retry")
        }
    }
    
}