package com.example.stcmarvelapp.data.dto

data class SeriesX(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)