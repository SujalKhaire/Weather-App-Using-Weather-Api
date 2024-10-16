package com.example.weatherapppractice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapppractice.Api.NtworkResponce
import com.example.weatherapppractice.Api.model

@Composable
fun weatherPage(weatherViewModel: weatherViewModel){

    val weaterModel = weatherViewModel.NtrResponce.observeAsState()
    var city by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(value = city, onValueChange = {city = it}, label = { Text(text = "ENTER CITY")}, modifier = Modifier.weight(1f), singleLine = true)
            IconButton(onClick = { weatherViewModel.getDta(city) }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null, modifier = Modifier.padding(top = 22.dp) )
            }
        }

        when(val reso = weaterModel.value){
            is NtworkResponce.Error -> {
                Text(text = reso.message)
            }
            is NtworkResponce.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp))
            }
            is NtworkResponce.Succsesfull<model> -> {
                weaterInfo(reso.data)
            }
            else -> {}
        }

    }
}

@Composable
fun weaterInfo(data:model){
    Column(modifier =
    Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription =null ,modifier = Modifier.size(40.dp))
            Text(text = data.location.name, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.location.country, fontSize = 18.sp, color = Color.Gray)

        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = " ${data.current.temp_c} ° c",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        AsyncImage(modifier = Modifier.size(160.dp),model = "https:${data.current.condition.icon}".replace("64x64","128x128"), contentDescription = null)
        Text(
            text = data.current.condition.text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Text(text = data.current.condition.text,fontSize = 20.sp,textAlign = TextAlign.Center,color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Card {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Humidity",data.current.humidity)
                    WeatherKeyVal("Wind Speed",data.current.wind_kph+" km/h")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("UV",data.current.uv)
                    WeatherKeyVal("Participation",data.current.precip_mm+" mm")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Local Time",data.location.localtime.split(" ")[1])
                    WeatherKeyVal("Local Date",data.location.localtime.split(" ")[0])
                }
            }
        }



    }

}

@Composable
fun WeatherKeyVal(key : String, value : String) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }
}



@Preview(showBackground = true)
@Composable
fun myPreview(){
    weatherPage(weatherViewModel())
}