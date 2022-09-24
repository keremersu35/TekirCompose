package com.example.tekirkotlin.utils

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.example.tekirkotlin.model.Cat
import com.example.tekirkotlin.utils.Constants.BREEDS
import com.orhanobut.hawk.Hawk

class FavListManager (private val context: Context) {

    var breedList= ArrayList<Cat>()
    var indexList = ArrayList<Int>()

    init {
        Hawk.init(context).build()
    }

    fun addBreed(cat: Cat){

        if(Hawk.get<ArrayList<Cat>>(BREEDS) != null){

            if(checkBreed(breedId = cat.id)){
                deleteBreed(breedId = cat.id)
            }
            else{
                breedList = Hawk.get(BREEDS)
                breedList.add(cat)
                Hawk.put(BREEDS, breedList)
            }
        }else{
            breedList.add(cat)
            Hawk.put(BREEDS, breedList)
        }
    }

    private fun deleteBreed(breedId: String){

        if(Hawk.get<ArrayList<String>>(BREEDS) != null){

            if(checkBreed(breedId = breedId)){
                breedList = Hawk.get(BREEDS)
                for(i in breedList){
                    if(i.id == breedId){
                        indexList.add(breedList.indexOf(i))
                    }
                }
                breedList.removeAt(indexList[0])
                Hawk.put(BREEDS, breedList)
            }
        }
    }

    fun checkBreed(breedId: String): Boolean{

        val list: ArrayList<Cat> = Hawk.get(BREEDS) ?: return false

        for(i in list){
            if(i.id == breedId) return true
        }
        return false
    }
}