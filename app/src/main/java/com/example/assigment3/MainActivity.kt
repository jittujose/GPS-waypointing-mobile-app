package com.example.assigment3

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                draughtsView(game_state = array,orientation_vector){x,y ->
                    placePiece(x,y)
                    updateGameStatus()
                }
                Text(text = rotation.value)
            }
        }


        var sm:SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null)
            sm.registerListener(rotation_listener,sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),SensorManager.SENSOR_DELAY_UI)
    }
    private fun placePiece(x:Int,y:Int) {

    }
    private fun updateGameStatus(){}

    private var rotation_listener: SensorEventListener = object : SensorEventListener{
        override fun onSensorChanged(event: SensorEvent?) {
            var rotation_matrix: FloatArray = FloatArray(16){0f}
            SensorManager.getRotationMatrixFromVector(rotation_matrix, event!!.values)
            SensorManager.getOrientation(rotation_matrix, orientation_vector)
            SensorManager.getRotationMatrixFromVector(rotation_matrix,event!!.values)

            orientation_vector[0] = Math.toDegrees(orientation_vector[0].toDouble()).toFloat()
            orientation_vector[1] = Math.toDegrees(orientation_vector[1].toDouble()).toFloat()
            orientation_vector[2] = Math.toDegrees(orientation_vector[2].toDouble()).toFloat()

            rotation.value = "roattion (bearing degrees) Z: ${String.format("%.2f",orientation_vector[0])} ms-2\n" +
                    "rotation (pitch degrees) X: ${String.format("%.2f", orientation_vector[1])} ms-2\n" +
                    "rotation (roll degrees) Y: ${String.format("%.2f", orientation_vector[2])} ms-2\n"
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            //we will do nothing here for the moment
        }
    }
    private var rotation = mutableStateOf("rotation vector: N/A")
    var orientation_vector: FloatArray =  FloatArray(4){0f}
}
val array = MutableList(1000) {
    MutableList(1000) { 0 }
}


