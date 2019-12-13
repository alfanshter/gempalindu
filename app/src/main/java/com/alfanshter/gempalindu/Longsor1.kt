package com.alfanshter.gempalindu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_longsor1.*

class Longsor1 : AppCompatActivity() {

    private lateinit var dref: DatabaseReference

    var status = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_longsor1)

        dref =FirebaseDatabase.getInstance().reference
        dref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
           status =  p0.child("Pergerakan/Ax").value!!.toString().toFloat()
                nilaitanah?.text = "$status"
            }
        })

    }
}
