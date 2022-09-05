package com.example.tekirkotlin.view.cat_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tekirkotlin.R
import com.example.tekirkotlin.model.Cat

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

                    SearchBar(hint = "Search...", modifier = Modifier
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
    var text = remember{
        mutableStateOf("")
    }

    var isHintDisplayed = remember{
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier){
        BasicTextField(value = text.value , onValueChange = {
            text.value = it
            onSearch(it)},
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed.value = it.isFocused != true && text.value.isEmpty()
                })

        if(isHintDisplayed.value){
            Text(text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp))
        }
    }
}

@Composable
fun CatRow(navController: NavController, cat: Cat) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(15.dp))
        .background(color = Color.White)
        .clickable {
            navController.navigate(
                "cat_detail_screen/${cat.id}"
            )
        }){

        Column(modifier = Modifier
            .weight(0.7f)
            .background(color = Color.White)
        ) {


            Text(text = cat.name,
                modifier = Modifier
                    .padding(12.dp)
                    .padding(bottom = 5.dp),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )

            Row(modifier = Modifier
                .wrapContentSize()
                .padding(12.dp)){
                
                Icon(painter = painterResource(id = R.drawable.marker),
                    contentDescription = "",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp))

                Text(text = cat.origin,
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.width(15.dp))

                Icon(painter = painterResource(id = R.drawable.life_span),

                    contentDescription = "",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp))
                
                Spacer(modifier = Modifier.width(3.dp))

                Text(text = cat.lifeSpan.toString(),
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
        }

        Box(modifier = Modifier
            .height(100.dp)
            .width(100.dp)
            .clip(RoundedCornerShape(10.dp))
        ) {
            if(cat.image?.url == null){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.cat)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.loading),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape)
                )
            }else{
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(cat.image?.url)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.loading),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(RoundedCornerShape(15.dp))
                )}
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
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
    LazyColumn(contentPadding = PaddingValues(5.dp)){
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