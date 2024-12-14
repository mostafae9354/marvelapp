package com.example.stcmarvelapp.data.repository

import com.example.stcmarvelapp.data.dto.CharactersDTO
import com.example.stcmarvelapp.data.source.MarvelCharactersService
import com.example.stcmarvelapp.domain.repository.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(private val service: MarvelCharactersService) :
    CharactersRepository {

    override suspend fun getCharactersList(
        offset: Int,
        searchText: String?
    ): CharactersDTO {
        return service.getCharactersList(offset = offset, searchText = searchText)
    }
}