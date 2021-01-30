package com.bignerdranch.android.photogallery

import android.content.Context
import android.util.Log
import androidx.lifecycle.*

private const val TAG = "PhotoGalleryViewModel"

class PhotoGalleryViewModel(private val context: Context): ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory(private val context: Context): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PhotoGalleryViewModel(context) as T
        }
    }

    private val flickrFetchr = FlickrFetchr()
    private val mutableSearchTerm = MutableLiveData(QueryPreferences.getStoredQuery(context))

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
        QueryPreferences.setStoredQuery(context, query)
        mutableSearchTerm.value = query
    }
}