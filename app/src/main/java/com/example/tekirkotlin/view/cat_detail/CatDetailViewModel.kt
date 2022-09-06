package com.example.tekirkotlin.view.cat_detail

import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import com.example.tekirkotlin.model.CatDetail
import com.example.tekirkotlin.model.Image
import com.example.tekirkotlin.repository.CatRepository
import com.example.tekirkotlin.utils.Constants
import com.example.tekirkotlin.utils.Constants.API_KEY
import com.example.tekirkotlin.utils.Constants.LIMIT
import com.example.tekirkotlin.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatDetailViewModel @Inject constructor(
    private val repository: CatRepository
) : ViewModel()
{
    suspend fun getCatInfo(id: String): Resource<CatDetail> {
        return repository.getCat(id)
    }

    suspend fun getCatPhotos(id: String): Resource<List<Image>> {
        return repository.getCatPhotos(LIMIT.toLong(), id, API_KEY)
    }
}
