package com.alfanshter.gempalindu

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_kelembapan.*
import kotlinx.android.synthetic.main.activity_longsor1.*

class Kelembapan : AppCompatActivity() {

    var nilaikelembapan = 0f
    lateinit var dref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kelembapan)
        dref =FirebaseDatabase.getInstance().reference
        kelembabantp.settings.javaScriptEnabled = true
        kelembabantp.loadUrl(Uri.parse("file:///android_asset/index1.html").toString())


        dref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                nilaikelembapan =  p0.child("Kelembapan/Nilai Kelembapan :").value!!.toString().toFloat()
                nilaihum?.text = "$nilaikelembapan"
            }
        })    }
}
