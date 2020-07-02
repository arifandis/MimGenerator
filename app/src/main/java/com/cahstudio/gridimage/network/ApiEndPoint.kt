package com.cahstudio.gridimage.network

import com.cahstudio.gridimage.model.response.MemeResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiEndPoint {

    @GET("get_memes")
    fun getMemes(): Call<MemeResponse>

}