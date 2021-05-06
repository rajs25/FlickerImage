package com.example.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PhotosViewModel : ViewModel() {
    private val mutablePhotosLiveData = MutableLiveData<List<Photo>>()
    val photosLiveData: MutableLiveData<List<Photo>> = mutablePhotosLiveData

//    init {
//        photosLiveData.value = mutablePhotosLiveData
//    }

    fun fetchImages(search : String, page : Int) {
        viewModelScope.launch {
            Log.d("Inside fetch Image :-", Thread.currentThread().name)
            val searchResponse = WebClient.client.fetchImages(search,page)
            val photosList = searchResponse.photos.photo.map { photo ->
                    Photo(
                        id = photo.id,
                        url = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg",
                        title = photo.title

                    )
            }
            mutablePhotosLiveData.postValue(photosList)
//            photosLiveData.value = mutablePhotosLiveData
//            Log.d("List Size", mutablePhotosLiveData.value!!.size.toString())
        }
    }
}
