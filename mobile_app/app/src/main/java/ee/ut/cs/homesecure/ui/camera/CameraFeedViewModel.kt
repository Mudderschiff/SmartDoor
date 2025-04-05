package ee.ut.cs.homesecure.ui.camera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ee.ut.cs.homesecure.service.HomeSecureApi
import kotlinx.coroutines.launch

class CameraFeedViewModel : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    init {
        getCameraIp()
    }

    private fun getCameraIp() {
        viewModelScope.launch {
            try {
                val camIpResults = HomeSecureApi.retrofitService.getCameraAddress()
                _status.value = "Success: $camIpResults Camera Ip Address retrieved"
                 status.value?.let { Log.i("-------Camera address", it) }
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}
