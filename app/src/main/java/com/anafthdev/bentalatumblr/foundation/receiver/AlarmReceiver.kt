package com.anafthdev.bentalatumblr.foundation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.anafthdev.bentalatumblr.foundation.common.NotificationManager

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("EXTRA_MESSAGE") ?: "Waktunya Minum!"
        val notificationId = intent.getIntExtra("EXTRA_ID", 0)

        // Tampilkan Notifikasi
        val service = NotificationManager(context)

        // Panggil fungsi untuk menampilkan notifikasi
        service.showNotification(
            notificationId = notificationId,
            title = "Pengingat Minum 💧",
            message = message
        )
    }
}
