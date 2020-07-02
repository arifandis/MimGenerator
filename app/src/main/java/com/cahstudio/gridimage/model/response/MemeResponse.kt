package com.cahstudio.gridimage.model.response

import com.cahstudio.gridimage.model.Meme

class MemeResponse(
    val success: Boolean,
    val data: Memes
){

    data class Memes(
        val memes: List<Meme>
    )
}