package com.harrisson.cardapioonline.models

import android.os.Parcelable

data class ParentItem(
    val title: String,
    val image: Int,
    val childItemList: ArrayList<ChildItem>,
    var isExpanded: Boolean = false
)

