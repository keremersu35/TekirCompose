package com.example.tekirkotlin.view.cat_fav_list

import android.content.Context
import androidx.compose.runtime.MutableState
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
    @ApplicationContext private val context: Context,
): ViewModel(){

    var catList = mutableStateOf<List<Cat>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var initialCatList = listOf<Cat>()
    private var isSearchStarting = true
    var breeds = ArrayList<Cat>()

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

    fun getCats(): MutableState<List<Cat>> {
        viewModelScope.launch {
            isLoading.value = true
            Hawk.init(context).build()
            breeds = Hawk.get(BREEDS)
            catList.value = breeds

            isLoading.value = false

        }
        return catList
    }
}