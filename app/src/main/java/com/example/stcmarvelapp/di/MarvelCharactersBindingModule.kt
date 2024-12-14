package com.example.stcmarvelapp.di

import com.example.stcmarvelapp.data.repository.CharacterDetailsRepositoryImpl
import com.example.stcmarvelapp.data.repository.CharactersRepositoryImpl
import com.example.stcmarvelapp.domain.repository.CharacterDetailsRepository
import com.example.stcmarvelapp.domain.repository.CharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class MarvelCharactersBindingModule {

    @Binds
    @ViewModelScoped
    abstract fun bindCharactersRepository(impl: CharactersRepositoryImpl): CharactersRepository

    @Binds
    @ViewModelScoped
    abstract fun bindCharacterDetailsRepository(impl: CharacterDetailsRepositoryImpl): CharacterDetailsRepository
}