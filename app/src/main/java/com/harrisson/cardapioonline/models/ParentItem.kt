package com.harrisson.cardapioonline.models

import android.os.Parcelable

data class ParentItem(
    val title: String,
    val image: Int,
    val childItemList: ArrayList<ChildItem>,
    var isExpanded: Boolean = false
): Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        parcel.readString().toString(),
        parcel.readInt(),
        arrayListOf<ChildItem>().apply {
            parcel.readList(this, ChildItem::class.java.classLoader)
        },
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(image)
        parcel.writeList(childItemList)
        parcel.writeByte(if (isExpanded) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParentItem> {
        override fun createFromParcel(parcel: android.os.Parcel): ParentItem {
            return ParentItem(parcel)
        }

        override fun newArray(size: Int): Array<ParentItem?> {
            return arrayOfNulls(size)
        }
    }
}
