package com.example.stcmarvelapp.domain.entity

data class MarvelCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val comics: List<String>,
    val imageUrl:String
)