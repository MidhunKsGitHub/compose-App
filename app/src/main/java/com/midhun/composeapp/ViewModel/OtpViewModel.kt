package com.midhun.composeapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midhun.composeapp.Model.OtpModel
import com.midhun.composeapp.Network.NetworkResultState
import com.midhun.composeapp.Repository.OtpRepository
import kotlinx.coroutines.launch

class OtpViewModel : ViewModel() {
    private val otpRepository: OtpRepository = OtpRepository()
    private val _otpModel = MutableLiveData<NetworkResultState<OtpModel>>()
    val otpModel: LiveData<NetworkResultState<OtpModel>> get() = _otpModel

    fun sendOtp(mobileNo: String, countryCode: String) {
        NetworkResultState.Loading(true)
        viewModelScope.launch {
            try {
                val otpData = otpRepository.sendOTP(mobileNo = mobileNo, countryCode = countryCode)
                if (otpData != null) {
                    _otpModel.value = NetworkResultState.Success(otpData)
                    NetworkResultState.Loading(false)
                } else {
                    _otpModel.value = NetworkResultState.Error(1, "Failed to fetch")
                    NetworkResultState.Loading(false)
                }
            } catch (e: Exception) {
                NetworkResultState.Exception(e)
            }
        }
    }
}