//package com.example.weatherapp.ui.component
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import com.example.weatherapp.R
//
//@Composable
//fun WeatherTips(weather: Weather){
//    if(weather.highTemp >= 20 && weather.name == "Sunny"){
//        Spacer(modifier = Modifier.height(24.dp))
//        Column(
//            verticalArrangement = Arrangement.SpaceEvenly,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//        ){
//            Text("It's a perfect day for the beach!\n\nDon't forget your sunscreen!!")
//            Image(
//                painter = painterResource(R.drawable.sunblock),
//                contentDescription = "Picture of sunblock",
//                modifier = Modifier.size(50.dp)
//            )
//        }
//    }
//    if(weather.name == "Rainy"){
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Text("Don't forget your umbrella!!")
//        Image(
//            painter = painterResource(R.drawable.umbrella),
//            contentDescription = "Picture of an umbrella",
//            modifier = Modifier.size(50.dp)
//        )
//    }
//}
