package com.example.stcmarvelapp.data.dto

data class StoriesX(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)