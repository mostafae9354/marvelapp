package com.example.stcmarvelapp.data.repository

import com.example.stcmarvelapp.data.dto.CharacterDetailsDTO
import com.example.stcmarvelapp.data.source.MarvelCharactersService
import com.example.stcmarvelapp.domain.repository.CharacterDetailsRepository
import javax.inject.Inject

class CharacterDetailsRepositoryImpl @Inject constructor(private val service: MarvelCharactersService) :
    CharacterDetailsRepository {

    override suspend fun getCharacterDetails(
        characterId: Int
    ): CharacterDetailsDTO {
        return service.getCharacterDetails(characterId = characterId)
    }
}