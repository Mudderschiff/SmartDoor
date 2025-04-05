package ee.ut.cs.homesecure.ui.lock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ee.ut.cs.homesecure.model.UnLock
import ee.ut.cs.homesecure.service.HomeSecureApi
import kotlinx.coroutines.launch

class LockViewModel : ViewModel() {
    private val _textDoor = MutableLiveData<String>().apply {
        value = "Unlock door...."
    }
    val textUnlock: LiveData<String> = _textDoor

    private val _recentLockStatus = MutableLiveData<String>()
    val statusLock: LiveData<String> = _recentLockStatus

    init {
        updateLockStatus()
    }

    private fun updateLockStatus() {
        viewModelScope.launch {
            try {
                val unlock = UnLock("unlock")
                val unlockResults = HomeSecureApi.retrofitService.unLockDoor(unlock)
                _recentLockStatus.value = "Success: $unlockResults"
                _recentLockStatus.value?.let { Log.i(TAG, it) }

            } catch (e: Exception) {
                _recentLockStatus.value = "Failure: ${e.message}"
            }
        }
    }

    companion object {
        private const val TAG = "LockViewModel"
    }

}
