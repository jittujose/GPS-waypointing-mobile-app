package com.example.assigment3

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import android.Manifest


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                mapView(game_state = array, orientation_vector) { x, y ->
                    placePiece(x, y)
                    updateGameStatus()
                }
                Text(text = rotation.value)
                Text(text = "${latitude.value}")
                Text(text = "${longitude.value}")
                Button(onClick = {
                    if (started_tracking.value){
                        removeLocationListener()}
                    else{
                        addLocationListener()}
                }) {
                    if(started_tracking.value){
                        Text(text = "Stop GPS tracking")}
                    else{
                        Text(text = "Start GPS tracking")}
                }


                val permission_launcher =
                    rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) {
                        if (!it.values.reduce { acc, next -> acc && next })
                            denied_qps.value = true
                        requested_qps.value = false
                    }
                if (requested_qps.value)
                    permission_launcher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION // Corrected typo here
                        )
                    )

                if (denied_qps.value) {
                    showWhyNotificationNeeded {
                        denied_qps.value = false
                    }
                }

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
    //code for location sensor
    private fun addLocationListener(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requested_qps.value = true
            return
        }

        var lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0f,location_listener)
        started_tracking.value=true
    }

    private fun removeLocationListener(){
        var lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        lm.removeUpdates(location_listener)
        latitude.value = "Latitude: N/A"
        longitude.value = "Longitude: N/A"
        started_tracking.value = false
    }

    var location_listener:LocationListener = LocationListener{
        latitude.value = "Latitude: ${it.latitude}"
        longitude.value = "Longitude: ${it.longitude}"
    }

    var latitude = mutableStateOf("Latitude: N/A")
    var longitude = mutableStateOf("Longitude: N/A")

    var requested_qps = mutableStateOf(false)
    var denied_qps = mutableStateOf(false)

    var started_tracking = mutableStateOf(false)

    private var rotation = mutableStateOf("rotation vector: N/A")
    var orientation_vector: FloatArray =  FloatArray(4){0f}
}
val array = MutableList(1000) {
    MutableList(1000) { 0 }
}



