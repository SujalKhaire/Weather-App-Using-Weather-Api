package com.example.weatherapppractice.Api

sealed class NtworkResponce<out T>
{
    data class Succsesfull<out T>(val data:T):NtworkResponce<T>()
    data class Error(val message:String):NtworkResponce<Nothing>()
    object loading:NtworkResponce<Nothing>()
}