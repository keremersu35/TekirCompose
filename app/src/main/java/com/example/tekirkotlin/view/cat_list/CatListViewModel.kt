package com.example.tekirkotlin.view.cat_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tekirkotlin.model.Cat
import com.example.tekirkotlin.repository.CatRepository
import com.example.tekirkotlin.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val repository: CatRepository
): ViewModel(){

    var catList = mutableStateOf<List<Cat>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var initialCryptoList = listOf<Cat>()
    private var isSearchStarting = true

    init {
        loadCats()
    }


    fun searchCatList(query: String) {
        val listToSearch = if(isSearchStarting) {
            catList.value
        } else {
            initialCryptoList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()) {
                catList.value = initialCryptoList
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.name.contains(query.trim(), ignoreCase = true)
            }
            if(isSearchStarting) {
                initialCryptoList = catList.value
                isSearchStarting = false
            }
            catList.value = results
        }
    }


    private fun loadCats(){
        viewModelScope.launch {
            isLoading.value = true
            when(val result = repository.getCats()){
                is Resource.Success -> {
                    val cats = result.data!!.mapIndexed { index, item ->
                        println(item.image)
                        Cat(item.id, item.name, item.origin, item.countryCode, item.description, item.image)
                    } as List<Cat>

                    errorMessage.value = ""
                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                }
            }
        }
    }
}