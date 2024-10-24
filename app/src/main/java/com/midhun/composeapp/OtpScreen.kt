package com.midhun.composeapp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.isTraceInProgress
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.midhun.composeapp.Model.OtpModel
import com.midhun.composeapp.Network.NetworkResultState
import com.midhun.composeapp.ViewModel.OtpViewModel


@Composable
fun OtpScreenView(viewModel: OtpViewModel, context: Context) {
    val otpData by viewModel.otpModel.observeAsState(NetworkResultState.Loading(true))



    OtpScreen(otpData!!, viewModel, context)


}

@Composable
fun OtpScreen(otpModel: NetworkResultState<OtpModel>, data: OtpViewModel, context: Context) {

    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var cc by rememberSaveable { mutableStateOf("65") }

    when (otpModel) {
        is NetworkResultState.Success -> {
            Toast.makeText(context, otpModel.data.message.toString(), Toast.LENGTH_LONG)
                .show()
        }

        is NetworkResultState.Error -> {
            Toast.makeText(context, otpModel.errorMessage.toString(), Toast.LENGTH_LONG)
                .show()
        }
        is NetworkResultState.Exception -> {
            Toast.makeText(context, otpModel.e.toString(), Toast.LENGTH_LONG)
                .show()
        }
        is NetworkResultState.Loading -> {
  if (otpModel.boolean == true)
  {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                TextField(
                    value = cc,
                    onValueChange = {
                        cc = it
                    },
                    label = { Text("Country Code") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                    },
                    label = { Text("Mobile No") }
                )


                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {
                    validate(context,phoneNumber,cc, data)
                })
                {
                    Text(text = "Send OTP")
                }
            }
        }
            else{
                CircularProgressIndicator()
  }
        }
    }


}

private fun validate(context: Context,ph:String,cc:String,data: OtpViewModel){
    if (ph.isEmpty()){
        Toast.makeText(context, "Enter phone", Toast.LENGTH_LONG)
            .show()
    }
    else if(cc.isEmpty()){
        Toast.makeText(context, "Enter country code", Toast.LENGTH_LONG)
            .show()
    }
    else{
        data.sendOtp(ph, cc)
    }
}