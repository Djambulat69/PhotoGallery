package com.bignerdranch.android.photogallery


import android.util.Log
import androidx.lifecycle.*

private const val TAG = "PhotoGalleryViewModel"

class PhotoGalleryViewModel: ViewModel() {

    private val flickrFetchr = FlickrFetchr()
    private val mutableSearchTerm = MutableLiveData("planets")

    val galleryItemLiveData: LiveData<List<GalleryItem>> = mutableSearchTerm.switchMap {
        liveData {
            emit(flickrFetchr.searchPhotos(it))
        }
    }

    fun fetchPhotos(query: String){
        mutableSearchTerm.value = query
    }
}