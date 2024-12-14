package com.example.stcmarvelapp.domain.entity

data class MarvelCharacterDetails(
    val id: String,
    val name: String,
    val description: String,
    val comics: List<String>,
    val series: List<String>,
    val stories: List<String>,
    val events: List<String>,
    val imageUrl:String
)