package com.example.stcmarvelapp.data.dto

import com.example.stcmarvelapp.domain.entity.MarvelCharacterDetails

data class ResultX(
    val comics: ComicsX,
    val description: String,
    val events: EventsX,
    val id: String,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: SeriesX,
    val stories: StoriesX,
    val thumbnail: Thumbnail,
    val urls: List<Url>
) {
    fun toCharacterDetails() = MarvelCharacterDetails(
        id = id,
        name = name,
        description = description,
        comics = comics.items.map {
            it.name
        },
        series = series.items.map {
            it.name
        },
        stories = stories.items.map {
            it.name
        },
        events = events.items.map {
            it.name
        },
        imageUrl = "${thumbnail.path.replace("http", "https")}.${thumbnail.extension}"
    )
}