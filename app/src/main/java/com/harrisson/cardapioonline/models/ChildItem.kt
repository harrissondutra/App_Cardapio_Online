package com.harrisson.cardapioonline.models

data class ChildItem(
    val title:String,
    val image: Int,
    val price: Double,
    val description: String,
    var qtd: Int = 0,
)
