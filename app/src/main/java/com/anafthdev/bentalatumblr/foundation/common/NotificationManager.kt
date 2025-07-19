package com.anafthdev.bentalatumblr.foundation.common

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.anafthdev.bentalatumblr.MainActivity
import com.anafthdev.bentalatumblr.R

class NotificationManager(
    private val context: Context
) {
    // Dapatkan instance NotificationManager dari sistem
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val REMINDER_CHANNEL_ID = "reminder"
        const val REMINDER_CHANNEL_NAME = "Drink Reminder"
    }

    /**
     * Fungsi utama untuk menampilkan notifikasi.
     * @param notificationId ID unik untuk setiap notifikasi.
     * @param title Judul notifikasi.
     * @param message Isi pesan notifikasi.
     */
    fun showNotification(notificationId: Int, title: String, message: String) {
        // 1. Buat channel notifikasi jika belum ada
        createNotificationChannel()

        // 2. Buat Intent untuk membuka aplikasi saat notifikasi di-tap
        val activityIntent = Intent(context, MainActivity::class.java).apply {
            // Flag ini penting untuk navigasi yang benar saat membuka dari notifikasi
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // 3. Bangun notifikasi menggunakan NotificationCompat.Builder
        val notification = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_water_drop)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Prioritas tinggi agar muncul sebagai heads-up
            .setContentIntent(activityPendingIntent) // Atur aksi saat di-tap
            .setAutoCancel(true) // Notifikasi akan hilang setelah di-tap
            .build()

        // 4. Tampilkan notifikasi
        notificationManager.notify(notificationId, notification)
    }

    /**
     * Membuat Notification Channel. Wajib untuk Android 8.0 (Oreo) ke atas.
     * Pengguna bisa mengatur preferensi notifikasi melalui channel ini di pengaturan sistem.
     */
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            REMINDER_CHANNEL_ID,
            REMINDER_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Channel untuk semua notifikasi pengingat minum."
            enableLights(true)
            enableVibration(true)
        }

        // Daftarkan channel ke sistem
        notificationManager.createNotificationChannel(channel)
    }
}
