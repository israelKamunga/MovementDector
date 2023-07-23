package com.isy_code.notionsadvanced

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.lifecycle.*
import com.isy_code.notionsadvanced.Entities.Coordinate
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewM = ViewModelProvider(this).get(VM::class.java)
        var coordinate = Coordinate("0","0");

        var Editext = findViewById<EditText>(R.id.edit_text)
        var button = findViewById<Button>(R.id.button2)
        var ConnectionStateImage = findViewById<ImageView>(R.id.imageview)
        var ConnectionStateTextView = findViewById<TextView>(R.id.textview)
        var animationDrawable : AnimationDrawable? = null

        val animation = AnimationUtils.loadAnimation(applicationContext,R.anim.animation)

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        button.setOnClickListener {
            ConnectionStateImage.setImageResource(R.drawable.ic_internet)
            ConnectionStateImage.startAnimation(animation)
            ConnectionStateTextView.text = "Connection in progress..."

            viewM.initialiserClient(Editext.text.toString())
            gyroscopeListenerInitialiser(viewM, sensorManager, gyroscope, coordinate)
        }

        viewM.connexionObserver.observe(this, Observer {value->
            when(value){
                "success"->{
                    ConnectionStateImage.clearAnimation()
                    ConnectionStateImage.setImageResource(R.drawable.ic_power)
                    ConnectionStateTextView.text = "Connection Established"
                }"failed"->{
                    ConnectionStateImage.clearAnimation()
                    ConnectionStateImage.setImageResource(R.drawable.ic_power_off)
                    ConnectionStateTextView.text = "Connection Failed"
                }else -> null
            }
        })
    }

    fun gyroscopeListenerInitialiser(viewM:VM,sensorManager:SensorManager,gyroscope:Sensor,coordinate: Coordinate){
        if(gyroscope!=null){
            sensorManager.registerListener(
                object : SensorEventListener{
                    override fun onSensorChanged(p0: SensorEvent?) {
                        coordinate.x = (p0?.values?.get(0)?.times((180/3.14))).toString()
                        coordinate.y = (p0?.values?.get(1)?.times((180/3.14))).toString()

                        viewM.putData(coordinate)
                    }

                    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

                    }
                },
                gyroscope,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }else{
            Toast.makeText(this,"une erreur s'est produite",Toast.LENGTH_SHORT).show()
        }
    }
}