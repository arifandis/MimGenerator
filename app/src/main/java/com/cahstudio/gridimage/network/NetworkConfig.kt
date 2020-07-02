package com.cahstudio.gridimage.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class NetworkConfig {
    companion object{
        fun getClient(): Retrofit {
            val client = OkHttpClient.Builder()
            client.connectTimeout(20, TimeUnit.SECONDS)
            client.readTimeout(20, TimeUnit.SECONDS)
            client.writeTimeout(20, TimeUnit.SECONDS)

            val gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .baseUrl("https://api.imgflip.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client.build())
                .build()
        }
    }
}