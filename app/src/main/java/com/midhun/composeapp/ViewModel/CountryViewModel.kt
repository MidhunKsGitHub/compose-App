package com.midhun.composeapp.ViewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midhun.composeapp.Model.Country
import com.midhun.composeapp.Network.NetworkResultState
import com.midhun.composeapp.Repository.CountryRepository
import kotlinx.coroutines.launch

class CountryViewModel : ViewModel(){

    private val repository=CountryRepository()
    private val _country=MutableLiveData<NetworkResultState<ArrayList<Country>>>()
   val country:LiveData<NetworkResultState<ArrayList<Country>>> = _country

    suspend fun getCountry(){
        viewModelScope.launch {
            try {

             val countries = repository.getCountry()
                if(countries!=null){
                    _country.value= NetworkResultState.Success(countries)
                }

            }
            catch (e:Exception){

            }
        }
    }

}