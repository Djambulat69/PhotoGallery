package com.bignerdranch.android.photogallery

import com.bignerdranch.android.photogallery.api.FlickrApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "FlickrFetchr"

class FlickrFetchr {


    private val flickrApi: FlickrApi

    init {
        val client = OkHttpClient.Builder()
                .addInterceptor(PhotoInterceptor())
                .build()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://www.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        flickrApi = retrofit.create(FlickrApi::class.java)
    }

    suspend fun fetchPhotos() : List<GalleryItem> = flickrApi.fetchPhotos().photos.galleryItems

    suspend fun searchPhotos(text: String)
    : List<GalleryItem> = flickrApi.searchPhotos(text).photos.galleryItems

}
