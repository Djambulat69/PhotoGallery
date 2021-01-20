package com.bignerdranch.android.photogallery


import android.app.Activity
import android.util.Log
import androidx.lifecycle.*

private const val TAG = "PhotoGalleryViewModel"

class PhotoGalleryViewModel(private val activity: Activity): ViewModel() {

    private val flickrFetchr = FlickrFetchr()
    private val mutableSearchTerm = MutableLiveData(QueryPreferences.getStoredQuery(activity))

    val searchTerm: String
        get() = mutableSearchTerm.value ?: ""

    val galleryItemLiveData: LiveData<List<GalleryItem>> = mutableSearchTerm.switchMap { query ->
        liveData {
            if (query.isBlank()) {
                emit(flickrFetchr.fetchPhotos())
            }
            else {
                emit(flickrFetchr.searchPhotos(query))
            }
        }
    }

    fun fetchPhotos(query: String){
        QueryPreferences.setStoredQuery(activity, query)
        mutableSearchTerm.value = query
    }
}