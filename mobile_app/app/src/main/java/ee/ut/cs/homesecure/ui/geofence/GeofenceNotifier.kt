package ee.ut.cs.homesecure.ui.geofence

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import ee.ut.cs.homesecure.R
import ee.ut.cs.homesecure.ui.map.MapFragment


private const val NOTIFICATION_ID = 33
private const val CHANNEL_ID = "GeofenceChannel"

fun createChannel(context: Context) {
    val notificationChannel =
        NotificationChannel(CHANNEL_ID, "Channel1", NotificationManager.IMPORTANCE_HIGH)
    val notificationManager = context.getSystemService(NotificationManager::class.java)
    notificationManager.createNotificationChannel(notificationChannel)
}

fun NotificationManager.sendGeofenceEnteredNotification(context: Context) {
    val contentIntent = Intent(context, MapFragment::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_IMMUTABLE)
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle(context.getString(R.string.app_name))
        .setContentText("You Just entered the Geo-fenced area around your home")
        .setSmallIcon(R.drawable.ic_notification)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(contentPendingIntent)
        .build()
    this.notify(NOTIFICATION_ID, builder)
}


