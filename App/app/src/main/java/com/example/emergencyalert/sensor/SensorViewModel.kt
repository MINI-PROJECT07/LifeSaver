package com.example.emergencyalert.sensor

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.emergencyalert.util.SendSms
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.S)
@HiltViewModel
class SensorViewModel @Inject constructor(
    private val accelerometerSensor: ParentSensor,
):ViewModel(){
    var value1 by mutableStateOf(0);

    fun pauseSensor(){
        accelerometerSensor.stopListening()
    }
    fun startSensor(){
        accelerometerSensor.startListening()
    }
    init {
        accelerometerSensor.startListening()
        accelerometerSensor.setOnSensorValuesChangedListener { values->
            Log.d("SensorViewModel", "onSensorValuesChanged: ${values}")
            value1 = values[0].toInt()
        }
    }
}