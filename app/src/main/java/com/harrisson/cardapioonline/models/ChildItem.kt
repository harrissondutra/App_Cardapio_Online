package com.harrisson.cardapioonline.models

import android.os.Parcelable
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.parcelize.Parcelize
@Parcelize
data class ChildItem(
    var id: String = "",
    var title: String? = null,
    var image: Int,
    var price: Double,
    var description: String? = null,
    var qtd: Int = 0,
) : Parcelable {
    init {
        this.id = Firebase.database.reference.push().key ?: ""
    }

}
