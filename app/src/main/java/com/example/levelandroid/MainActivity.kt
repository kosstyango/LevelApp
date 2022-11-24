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
    lateinit var sManager : SensorManager
    private var magnetic = FloatArray(9)
    private var gravity = FloatArray(9)

    private var accrs = FloatArray(3)
    private var magf = FloatArray(3)
    private var values = FloatArray(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvSensor = findViewById<TextView>(R.id.tvSensor)
        val lRotation = findViewById<LinearLayout>(R.id.lRotation)
        sManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val sensor2 = sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        val sListener = object : SensorEventListener {
            override fun onSensorChanged(event : SensorEvent?) {
                when (event?.sensor?.type){
                    Sensor.TYPE_ACCELEROMETER -> accrs = event.values.clone()
                    Sensor.TYPE_MAGNETIC_FIELD -> magf = event.values.clone()
                }
                SensorManager.getRotationMatrix(gravity, magnetic, accrs, magf)
                val outGravity = FloatArray(9)
                SensorManager.remapCoordinateSystem(gravity, SensorManager.AXIS_X, SensorManager.AXIS_Z, outGravity)
                SensorManager.getOrientation(outGravity, values)
                val degree = values[2] * 57.2958f
                lRotation.rotation = 270 + degree
                tvSensor.text = (90+degree).toInt().toString()
                val color = if((90+degree).toInt() == 0){
                    Color.GREEN}
                    else{
                    Color.RED}
                lRotation.setBackgroundColor(color)

                }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                TODO("Not yet implemented")
            }
        }
        sManager.registerListener(sListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        sManager.registerListener(sListener, sensor2, SensorManager.SENSOR_DELAY_NORMAL)
    }
}