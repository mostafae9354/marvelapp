package com.example.stcmarvelapp.presentation.character_details.view

import com.example.stcmarvelapp.domain.entity.MarvelCharacterDetails

data class CharacterDetailsUiState(
    val isLoading: Boolean = false,
    val characterDetails: MarvelCharacterDetails? = null,
    val error: String? = null
)