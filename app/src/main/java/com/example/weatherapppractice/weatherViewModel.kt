package com.example.weatherapppractice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapppractice.Api.NtworkResponce
import com.example.weatherapppractice.Api.RetrofitInstance
import com.example.weatherapppractice.Api.model
import kotlinx.coroutines.launch

class weatherViewModel:ViewModel()
{
    val _ins = RetrofitInstance.weatherApi
    private val _NtrResponse = MutableLiveData<NtworkResponce<model>>()
    val NtrResponce: LiveData<NtworkResponce<model>> = _NtrResponse

     fun getDta(city:String){
        _NtrResponse.value = NtworkResponce.loading


         viewModelScope.launch {
             val responce = _ins.watherApi("e4827b49d04847b1b01122037242109",city)

             try {
                 if (responce.isSuccessful)
                 {
                     responce.body()?.let {
                         _NtrResponse.value = NtworkResponce.Succsesfull(it)
                     }
                 }
                 else{
                     _NtrResponse.value = NtworkResponce.Error("SOMETHING WRONG")
                 }
             }
             catch (e:Exception){
                 _NtrResponse.value = NtworkResponce.Error("SOMETHING WRONG")
             }

         }

    }
}