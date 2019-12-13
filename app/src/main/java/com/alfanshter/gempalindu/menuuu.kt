package com.alfanshter.gempalindu

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.pusher.pushnotifications.PushNotifications
import kotlinx.android.synthetic.main.activity_info_longsor.*
import kotlinx.android.synthetic.main.activity_menuuu.*
import org.jetbrains.anko.startActivity
import kotlin.math.roundToInt

class menuuu : AppCompatActivity() {
    var v_flipper: ViewFlipper? = null
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
        setContentView(R.layout.activity_menuuu)
        PushNotifications.start(applicationContext, "ca294a3b-8615-452a-83a7-a815fb3f4dd4")
        PushNotifications.addDeviceInterest("hello")
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        dref = FirebaseDatabase.getInstance().reference
        dref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                logika = p0.child("Notifikasi/Nilai Logika :").value!!.toString().toFloat().roundToInt()
                if (logika >0)
                {
                    val intent = Intent(this@menuuu, LauncherActivity::class.java)
                    val pendingIntent = PendingIntent.getActivity(this@menuuu,0,intent,
                        PendingIntent.FLAG_UPDATE_CURRENT)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        notificationChannel = NotificationChannel(channelID,description,NotificationManager.IMPORTANCE_HIGH)
                        notificationChannel.enableLights(true)
                        notificationChannel.lightColor = Color.GREEN
                        notificationChannel.enableVibration(false)
                        notificationManager.createNotificationChannel(notificationChannel)

                        builder = Notification.Builder(this@menuuu,channelID)
                            .setContentTitle("NOTIFIKASI")
                            .setContentText("TERDETEKSI LONGSOR")
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setLargeIcon(BitmapFactory.decodeResource(this@menuuu.resources,R.mipmap.ic_launcher))
                            .setContentIntent(pendingIntent)
                    }
                    notificationManager.notify(1234,builder.build())

                }

            }
        })

        val images =
            intArrayOf(R.drawable.llongsor1, R.drawable.llongsor2, R.drawable.llongsor3 )
        v_flipper = findViewById(R.id.slider_menu)

        for (i in images.indices) {
            fliverImages(images[i])
        }
        for (image in images) fliverImages(image)
        longsor.setOnClickListener {
            startActivity<Longsor1>()
        }

        profil.setOnClickListener {
            startActivity<Profil>()
        }

        kelembapan.setOnClickListener {
            startActivity<Kelembapan>()
        }

        info.setOnClickListener {
            startActivity<InfoLongsor>()
        }

    }
    fun fliverImages(images: Int) {
        val imageView = ImageView(this)
        imageView.setBackgroundResource(images)
        v_flipper?.addView(imageView)
        v_flipper?.setFlipInterval(4000)
        v_flipper?.setAutoStart(true)
        v_flipper?.setInAnimation(this, android.R.anim.slide_in_left)
        v_flipper?.setOutAnimation(this, android.R.anim.slide_out_right)
    }
    }

