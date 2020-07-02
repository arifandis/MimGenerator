package com.cahstudio.gridimage.ui.main

import com.cahstudio.gridimage.model.response.MemeResponse

interface MainContract {

    interface View{
        fun getResponse(memeResponse: MemeResponse?)
        fun showError(msg: String?)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter{
        fun memeList()
    }
}