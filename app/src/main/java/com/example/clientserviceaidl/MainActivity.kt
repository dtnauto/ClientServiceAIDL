package com.example.clientserviceaidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import com.example.serviceaidl.AIDLInterface

class MainActivity : AppCompatActivity() {

    var AIDLService: AIDLInterface? = null
    var mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            AIDLService = AIDLInterface.Stub.asInterface(service)
            Log.e("mycodeisblocking", "Remote config Service Connected!")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e("mycodeisblocking", "Noconectd")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener{
            var intent = Intent("com.example.serviceaidl.ChangeBackgroundColor")
//            intent.setClassName("com.example.serviceaidl","com.example.serviceaidl.ChangeBackgroundColor")
            intent.setPackage("com.example.serviceaidl")

            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)

            try {
                var color = AIDLService?.getBackgroundColor()
                if (color != null) {
                    it.setBackgroundColor(color)
                }

            }catch (e : Exception){

                Log.e("mycodeisblocking","$e")
            }
        }
    }
}