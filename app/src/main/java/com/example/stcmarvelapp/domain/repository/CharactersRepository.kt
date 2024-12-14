package com.example.stcmarvelapp.domain.repository

import com.example.stcmarvelapp.data.dto.CharactersDTO

interface CharactersRepository {
    suspend fun getCharactersList(offset: Int, searchText: String? = null): CharactersDTO
}