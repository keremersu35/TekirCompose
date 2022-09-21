package com.example.tekirkotlin.view.cat_fav_list

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tekirkotlin.model.Cat
import com.example.tekirkotlin.repository.CatRepository
import com.example.tekirkotlin.utils.Constants.BREEDS
import com.example.tekirkotlin.utils.FavListManager
import com.example.tekirkotlin.utils.Resource
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatFavListViewModel @Inject constructor(
    private val repository: CatRepository,
    @ApplicationContext private val context: Context,
): ViewModel(){

    var catList = mutableStateOf<List<Cat>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var initialCatList = listOf<Cat>()
    private var isSearchStarting = true
    var breedList = ArrayList<Cat>()
    var breeds = ArrayList<String>()

    fun searchCatList(query: String) {
        val listToSearch = if(isSearchStarting) {
            catList.value
        } else {
            initialCatList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()) {
                catList.value = initialCatList
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.name.contains(query.trim(), ignoreCase = true)
            }
            if(isSearchStarting) {
                initialCatList = catList.value
                isSearchStarting = false
            }
            catList.value = results
        }
    }

    private fun getCatIds(): ArrayList<String>{
        Hawk.init(context).build()
        breeds = Hawk.get(BREEDS)
        return breeds
    }

    fun getCats(){
        val breeds = getCatIds()
        for(i in breeds){
            println("sonuc$i")
            loadCat(i)
        }
        catList.value = breedList
    }

    private fun loadCat(breedId: String){
        viewModelScope.launch {
            isLoading.value = true
            when(val result = repository.getCat(breedId)){
                is Resource.Success -> {
                    val cat = result.data!!
                    breedList.add(Cat(cat.id, cat.name, cat.origin, cat.countryCode, cat.description, null, cat.lifeSpan))
                    println("kerem"+catList.value)
                    errorMessage.value = ""
                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                }
                else -> {}
            }
        }
    }
}