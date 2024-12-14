package com.example.stcmarvelapp.data.dto

data class Stories(
    val available: String,
    val collectionURI: String,
    val items: List<Item>,
    val returned: String
)