package com.bignerdranch.android.photogallery

import android.app.PendingIntent
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

private const val TAG = "PollWorker"
private const val PREF_NAME = "com.bignerdranch.android.photogallery.PREF_NAME"

class PollWorker(private val appContext: Context, workerParameters: WorkerParameters)
    : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        val query = QueryPreferences.getStoredQuery(appContext)
        val lastResultId = QueryPreferences.getLastResultId(appContext)
        val items: List<GalleryItem> = if (query.isEmpty()) {
            FlickrFetchr().fetchPhotos()
        } else {
            FlickrFetchr().searchPhotos(query)
        }

        val resultId = items.first().id
        if (resultId == lastResultId) {
            Log.i(TAG, "Got an old result: $resultId")
        } else {
            Log.i(TAG, "Got a new result: $resultId")
            QueryPreferences.setLastResultId(appContext, resultId)

            val intent = PhotoGalleryActivity.newIntent(appContext)
            val pendingIntent = PendingIntent.getActivity(appContext, 0, intent, 0)
            val resources = appContext.resources
            val notification = NotificationCompat
                .Builder(appContext, NOTIFICATION_CHANNEL_ID)
                .setTicker(resources.getString(R.string.new_pictures_title))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(resources.getString(R.string.new_pictures_title))
                .setContentText(resources.getString(R.string.new_pictures_text))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
            val notificationManager = NotificationManagerCompat.from(appContext)
            notificationManager.notify(0, notification)
        }


        return Result.success()
    }
}