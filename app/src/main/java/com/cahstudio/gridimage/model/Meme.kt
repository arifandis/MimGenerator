package com.cahstudio.gridimage.model

import android.os.Parcel
import android.os.Parcelable

//"id": "181913649",
//"name": "Drake Hotline Bling",
//"url": "https:\/\/i.imgflip.com\/30b1gx.jpg",
//"width": 1200,
//"height": 1200,
//"box_count": 2
data class Meme(
    val id: String? = null,
    val name: String? = null,
    val url: String? = null,
    val width: Int,
    val height: Int,
    val box_count: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeInt(box_count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Meme> {
        override fun createFromParcel(parcel: Parcel): Meme {
            return Meme(parcel)
        }

        override fun newArray(size: Int): Array<Meme?> {
            return arrayOfNulls(size)
        }
    }
}