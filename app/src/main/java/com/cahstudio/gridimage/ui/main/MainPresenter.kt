package com.cahstudio.gridimage.ui.main

import com.cahstudio.gridimage.model.response.MemeResponse
import com.cahstudio.gridimage.network.ApiEndPoint
import com.cahstudio.gridimage.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter {
    companion object{
        private lateinit var mView: MainContract.View
        private lateinit var mApi: ApiEndPoint

        fun getInstance(view: MainContract.View): MainPresenter{
            mView = view
            mApi = NetworkConfig.getClient().create(ApiEndPoint::class.java)
            return MainPresenter()
        }
    }

    fun memeList(){
        mView.showLoading()
        mApi.getMemes().enqueue(object : Callback<MemeResponse>{
            override fun onFailure(call: Call<MemeResponse>, t: Throwable) {
                mView.hideLoading()
                mView.showError(t.localizedMessage)
            }

            override fun onResponse(call: Call<MemeResponse>, response: Response<MemeResponse>) {
                mView.hideLoading()
                if (response.isSuccessful){
                    mView.getResponse(response.body())
                }else{
                    mView.showError("Internal server error")
                }
            }
        })
    }
}