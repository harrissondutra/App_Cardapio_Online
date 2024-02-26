package com.harrisson.cardapioonline.models

import android.os.Parcel
import android.os.Parcelable

data class ChildItem(
    var title:String? = null,
    val image: Int,
    var price: Double,
    val description: String? = null,
    var qtd: Int = 0,
): Parcelable {
    constructor(parcel : Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel : Parcel, flags : Int) {
        parcel.writeString(title)
        parcel.writeInt(image)
        parcel.writeDouble(price)
        parcel.writeString(description)
        parcel.writeInt(qtd)
    }

    override fun describeContents() : Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChildItem> {
        override fun createFromParcel(parcel : Parcel) : ChildItem {
            return ChildItem(parcel)
        }

        override fun newArray(size : Int) : Array<ChildItem?> {
            return arrayOfNulls(size)
        }
    }
}
