package com.cahstudio.gridimage.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cahstudio.gridimage.R
import com.cahstudio.gridimage.model.Meme
import com.cahstudio.gridimage.model.response.MemeResponse
import com.cahstudio.gridimage.ui.detail.DetailMemeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var mPresenter: MainPresenter
    private lateinit var mAdapter: MemeAdapter

    private var mMemeList: MutableList<Meme> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter = MainPresenter.getInstance(this)
        val layoutManager = GridLayoutManager(this,3)
        mAdapter =
            MemeAdapter(this, mMemeList) { meme: Meme -> gotoDetail(meme) }
        main_recyclerview.layoutManager = layoutManager
        main_recyclerview.adapter = mAdapter

        main_swiperefresh.setOnRefreshListener(this)
        mPresenter.memeList()
    }

    fun gotoDetail(meme: Meme){
        val intent = Intent(this, DetailMemeActivity::class.java)
        intent.putExtra("meme",meme)
        startActivity(intent)
    }

    override fun getResponse(memeResponse: MemeResponse?) {
        if (memeResponse != null){
            if (memeResponse.success){
                mMemeList.clear()
                mMemeList.addAll(memeResponse.data.memes)
                mAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this,"List kosong",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun showError(msg: String?) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        main_progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        main_progressBar.visibility = View.GONE
        main_swiperefresh.isRefreshing = false
    }

    override fun onRefresh() {
        mPresenter.memeList()
    }
}