package com.example.stcmarvelapp.presentation.characters_list.view

import com.example.stcmarvelapp.domain.entity.MarvelCharacter

data class CharactersListUiState(
    val isLoading: Boolean = false,
    val charactersList: List<MarvelCharacter>? = null,
    val error: String? = null
)