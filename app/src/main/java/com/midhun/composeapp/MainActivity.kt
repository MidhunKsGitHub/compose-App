package com.midhun.composeapp

import android.os.Bundle
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import com.midhun.composeapp.Model.Country
import com.midhun.composeapp.Model.OtpModel
import com.midhun.composeapp.Network.NetworkResultState

import com.midhun.composeapp.ViewModel.CountryViewModel
import com.midhun.composeapp.ViewModel.OtpViewModel
import com.midhun.composeapp.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: CountryViewModel by viewModels()
    private val otpViewModel: OtpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                CardScreen(viewModel = viewModel!!)
                //OtpScreenView(viewModel = otpViewModel,this@MainActivity)

            }
        }
    }

    @Composable
    private fun scrapeWebsiteData() {
        otpViewModel.otpModel.observe(this, Observer {

        })
    }

}


@Composable
fun CardScreen(viewModel: CountryViewModel) {
    val countryCards by viewModel.country.observeAsState(NetworkResultState.Loading(true))


    LaunchedEffect(Unit) {
        viewModel.getCountry()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()

    ) {
        list(countryCards = countryCards!!)

    }

}

@Composable
fun list(countryCards: NetworkResultState<ArrayList<Country>>) {
    when (countryCards) {
        is NetworkResultState.Error -> TODO()
        is NetworkResultState.Exception -> TODO()
        is NetworkResultState.Loading -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray),
            ) {
                Text(text = "Loading...")
                Spacer(modifier = Modifier.height(20.dp))
                CircularProgressIndicator()
            }
        }
        is NetworkResultState.Success -> {
            LazyColumn {
                items(countryCards.data) { cCard ->
                    CountryCard(
                        currency = cCard.currencySymbol.toString(),
                        name = cCard.name.toString(),
                        code = cCard.currencyCode.toString(),
                        currencyName = cCard.currencyName.toString()
                    )
                    // Add a divider between items
                }
            }

        }

        null -> TODO()
    }

}


@Composable
fun CountryCard(currency: String, name: String, code: String, currencyName: String) {

    Card(colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .padding(10.dp)
            .clickable { }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterVertically)
                    .fillMaxSize()

            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = currency, fontSize = 20.sp, textAlign = TextAlign.Center,
                    )
                }

            }
            Column(Modifier.padding(10.dp)) {
                Text(text = name, fontSize = 20.sp)
                Text(text = code, fontSize = 20.sp)
                Text(text = currencyName, fontSize = 20.sp)
            }

        }

    }

}

@Preview(showSystemUi = true)
@Composable
fun preview() {
    CountryCard("1", "1", "1", "1")
    //CardScreen(viewModel = CountryViewModel())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(function: () -> Unit) {

    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        CountryList()
    }
}

@Composable
fun CountryList() {
    val countries = listOf(
        Pair("United States", "\uD83C\uDDFA\uD83C\uDDF8"),
        Pair("Canada", "\uD83C\uDDE8\uD83C\uDDE6"),
        Pair("India", "\uD83C\uDDEE\uD83C\uDDF3"),
        Pair("Germany", "\uD83C\uDDE9\uD83C\uDDEA"),
        Pair("France", "\uD83C\uDDEB\uD83C\uDDF7"),
        Pair("Japan", "\uD83C\uDDEF\uD83C\uDDF5"),
        Pair("China", "\uD83C\uDDE8\uD83C\uDDF3"),
        Pair("Brazil", "\uD83C\uDDE7\uD83C\uDDF7"),
        Pair("Australia", "\uD83C\uDDE6\uD83C\uDDFA"),
        Pair("Russia", "\uD83C\uDDF7\uD83C\uDDFA"),
        Pair("United Kingdom", "\uD83C\uDDEC\uD83C\uDDE7"),
    )

    LazyColumn {
        items(countries) { (country, flag) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp)
            ) {

                Text(
                    text = flag, modifier = Modifier.padding(end = 20.dp)
                )
                Text(text = country)
            }
        }
    }
}

