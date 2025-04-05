package ee.ut.cs.homesecure.ui.rfid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ee.ut.cs.homesecure.service.HomeSecureApi
import kotlinx.coroutines.launch

class RfidViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "RFID sensor data..."
    }
    val text: LiveData<String> = _text


    private val _recentRfidStatus = MutableLiveData<String>()
    val statusRfid: LiveData<String> = _recentRfidStatus

    init {
        getRFIDAlerts()
    }

    private fun getRFIDAlerts() {
        viewModelScope.launch {
            try {
                val rfidResults = HomeSecureApi.retrofitService.getRfidAlerts()
                _recentRfidStatus.value = "Success: $rfidResults"
            } catch (e: Exception) {
                _recentRfidStatus.value = "Failure: ${e.message}"
            }
        }
    }
}
