package com.example.tekirkotlin.utils

import android.content.Context
import com.example.tekirkotlin.utils.Constants.BREEDS
import com.orhanobut.hawk.Hawk

class FavListManager (private val context: Context) {

    var breedList= ArrayList<String>()

    init {
        Hawk.init(context).build()
    }

    fun saveBreed(breedId: String) {

        breedList = Hawk.get(BREEDS)

        if(breedList != null){

            if(checkBreed(breedId = breedId)){
                breedList.remove(breedId)
                Hawk.put(BREEDS, breedList)
            }else{
                breedList.add((breedId))
                Hawk.put(BREEDS, breedList)
            }

        }else{
            breedList.add(breedId)
            Hawk.put(BREEDS, breedList)
        }
    }

    fun checkBreed(breedId: String): Boolean{

        val list: ArrayList<String> = Hawk.get(BREEDS) ?: return false

        if(list.contains(breedId)){
            return true
        }
        return false
    }
}