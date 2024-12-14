package com.example.stcmarvelapp.domain.usecase

import com.example.stcmarvelapp.core.utils.Response
import com.example.stcmarvelapp.domain.entity.MarvelCharacterDetails
import com.example.stcmarvelapp.domain.repository.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(
    private val repository: CharacterDetailsRepository
) {
    operator fun invoke(characterId: Int): Flow<Response<MarvelCharacterDetails>> = flow {

        try {
            emit(Response.Loading())

            val characterDetails =
                repository.getCharacterDetails(characterId).data.results.firstOrNull()
                    ?.toCharacterDetails()
            emit(Response.Success(characterDetails))
        } catch (e: Exception) {
            emit(Response.Error(e.message))
        }
    }
}