package com.cahstudio.gridimage.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cahstudio.gridimage.R
import com.cahstudio.gridimage.model.Meme
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_meme.view.*

class MemeAdapter(var context: Context?, var memeList: List<Meme>
                  , val listener: (Meme) -> Unit): RecyclerView.Adapter<MemeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_meme,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = memeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(memeList[position],listener)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val ivMeme = view.itemMeme_iv

        fun bindItem(meme: Meme, listener: (Meme) -> Unit){
            Picasso.get().load(meme.url).into(ivMeme)

            itemView.setOnClickListener {
                listener(meme)
            }
        }
    }
}