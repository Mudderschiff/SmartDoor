package ee.ut.cs.homesecure.ui.sensor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ee.ut.cs.homesecure.service.HomeSecureApi
import kotlinx.coroutines.launch

class SensorModel : ViewModel() {

    private val _motion = MutableLiveData<String>().apply {
        value = "Motion sensor data..."
    }
    private val _knock = MutableLiveData<String>().apply {
        value = "Knock sensor data..."
    }
    val textMotion: LiveData<String> = _motion
    val textKnock: LiveData<String> = _knock

    private val _recentMotionStatus = MutableLiveData<String>()
    val statusMotionSensors: LiveData<String> = _recentMotionStatus

    private val _recentKnockStatus = MutableLiveData<String>()
    val statusKnockSensors: LiveData<String> = _recentKnockStatus

    init {
        getKnockAlerts()
        getMotionAlerts()
    }

    private fun getMotionAlerts() {
        viewModelScope.launch {
            try {
                val sensorMotionResults = HomeSecureApi.retrofitService.getMotionAlerts()
                _recentMotionStatus.value = "Success: $sensorMotionResults"
            } catch (e: Exception) {
                _recentMotionStatus.value = "Failure: ${e.message}"
            }
        }
    }
    private fun getKnockAlerts() {
        viewModelScope.launch {
            try {
                val sensorKnockResults = HomeSecureApi.retrofitService.getKnockAlerts()
                _recentKnockStatus.value = "Success: $sensorKnockResults"
            } catch (e: Exception) {
                _recentKnockStatus.value = "Failure: ${e.message}"
            }
        }
    }
}
