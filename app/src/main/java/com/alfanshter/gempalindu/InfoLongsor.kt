package com.alfanshter.gempalindu

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import com.pusher.pushnotifications.PushNotifications
import kotlinx.android.synthetic.main.activity_info_longsor.*
import kotlinx.android.synthetic.main.activity_kelembapan.*
import kotlin.math.roundToInt

class InfoLongsor : AppCompatActivity() {

    private lateinit var dref : DatabaseReference
    var nilaigerak = 0f
    private lateinit var key:String
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder : Notification.Builder
    private val channelID = "com.alfanshter.gempalindu"
    private val description = "Test notification"
    var logika = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_longsor)
        PushNotifications.start(applicationContext, "ca294a3b-8615-452a-83a7-a815fb3f4dd4")
        PushNotifications.addDeviceInterest("hello")
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        dref = FirebaseDatabase.getInstance().reference
        dref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                logika = p0.child("Notifikasi/Nilai Logika :").value!!.toString().toFloat().roundToInt()
                nilaigerak =  p0.child("Pergerakan/Gy").value!!.toString().toFloat()
                if (logika >0)
                {
                    val intent = Intent(this@InfoLongsor, LauncherActivity::class.java)
                    val pendingIntent = PendingIntent.getActivity(this@InfoLongsor,0,intent,
                        PendingIntent.FLAG_UPDATE_CURRENT)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        notificationChannel = NotificationChannel(channelID,description,NotificationManager.IMPORTANCE_HIGH)
                        notificationChannel.enableLights(true)
                        notificationChannel.lightColor = Color.GREEN
                        notificationChannel.enableVibration(false)
                        notificationManager.createNotificationChannel(notificationChannel)

                        builder = Notification.Builder(this@InfoLongsor,channelID)
                            .setContentTitle("NOTIFIKASI")
                            .setContentText("TERDETEKSI BENCANA LONGSOR")
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setLargeIcon(BitmapFactory.decodeResource(this@InfoLongsor.resources,R.mipmap.ic_launcher))
                            .setContentIntent(pendingIntent)
                    }
                    notificationManager.notify(1234,builder.build())

                }
                
                if (nilaigerak >1000 )
                {
                    statusgerak.text = "STATUS BAHAYA"
                }
                else
                {
                    statusgerak.text = "STATUS AMAN"
                }
            }
        })
    }
}
