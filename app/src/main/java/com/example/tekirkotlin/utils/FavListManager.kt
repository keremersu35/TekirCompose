package com.example.tekirkotlin.utils

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.tekirkotlin.utils.Constants.BREEDS
import com.orhanobut.hawk.Hawk

class FavListManager (private val context: Context) {

    var breedList= ArrayList<String>()

    init {
        Hawk.init(context).build()
    }

    fun addBreed(breedId: String){

        if(Hawk.get<ArrayList<String>>(BREEDS) != null){

            if(checkBreed(breedId = breedId)){
                deleteBreed(breedId = breedId)
            }
            else{
                breedList = Hawk.get(BREEDS)
                breedList.add(breedId)
                Hawk.put(BREEDS, breedList)
            }
        }else{
            breedList.add(breedId)
            Hawk.put(BREEDS, breedList)
        }
    }

    private fun deleteBreed(breedId: String){

        if(Hawk.get<ArrayList<String>>(BREEDS) != null){

            if(checkBreed(breedId = breedId)){
                breedList = Hawk.get(BREEDS)
                breedList.remove(breedId)
                Hawk.put(BREEDS, breedList)
            }
        }else{

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