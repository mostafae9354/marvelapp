package com.example.stcmarvelapp.domain.usecase

import com.example.stcmarvelapp.core.utils.Response
import com.example.stcmarvelapp.domain.entity.MarvelCharacter
import com.example.stcmarvelapp.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharactersListUseCase @Inject constructor(private val repository: CharactersRepository) {
    operator fun invoke(offset: Int, searchText: String? = null): Flow<Response<List<MarvelCharacter>>> = flow {

        try {
            emit(Response.Loading())

            val charactersList = repository.getCharactersList(offset, searchText).data.results.map {
                it.toCharacter()
            }
            emit(Response.Success(charactersList))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response.Error(e.message))
        }
    }
}