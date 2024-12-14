package com.example.stcmarvelapp.domain.repository

import com.example.stcmarvelapp.data.dto.CharacterDetailsDTO

interface CharacterDetailsRepository {
    suspend fun getCharacterDetails(
        characterId: Int
    ): CharacterDetailsDTO
}