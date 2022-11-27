package com.example.levelandroid

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var sManager: SensorManager
    private var accrs = FloatArray(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvSensor = findViewById<TextView>(R.id.tvSensor)
        val lRotation = findViewById<LinearLayout>(R.id.lRotation)
        sManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val sListener = object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent?) {
                when (event?.sensor?.type) {
                    Sensor.TYPE_ACCELEROMETER -> accrs = event.values.clone()
                }
                val value = event?.values
                val sData = (value?.get(1)?.times(9.1743f))?.toInt()
                tvSensor.text = sData.toString()
                lRotation.rotation = sData?.toFloat()!!

                val color = if(sData == 0){
                    Color.GREEN}
                    else{
                    Color.RED}
                lRotation.setBackgroundColor(color)
            }

            //
            override fun onAccuracyChanged(event: Sensor?, p1: Int) {
//                TODO("Not yet implemented")
            }
        }
            sManager.registerListener(sListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }