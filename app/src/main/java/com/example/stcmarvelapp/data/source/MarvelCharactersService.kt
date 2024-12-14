package com.example.stcmarvelapp.data.source

import com.example.stcmarvelapp.core.Constants
import com.example.stcmarvelapp.data.dto.CharacterDetailsDTO
import com.example.stcmarvelapp.data.dto.CharactersDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelCharactersService {

    @GET("/v1/public/characters")
    suspend fun getCharactersList(
        @Query("apikey") apikey: String = Constants.API_KEY,
        @Query("ts") ts: String = Constants.timestamp,
        @Query("hash") hash: String = Constants.hash(),
        @Query("limit") limit: Int = Constants.LIMIT,
        @Query("offset") offset: Int,
        @Query("nameStartsWith") searchText: String? = null
    ): CharactersDTO

    @GET("/v1/public/characters/{characterId}}")
    suspend fun getCharacterDetails(
        @Path("characterId") characterId: Int,
        @Query("apikey") apikey: String = Constants.API_KEY,
        @Query("ts") ts: String = Constants.timestamp,
        @Query("hash") hash: String = Constants.hash()
    ): CharacterDetailsDTO
}