package com.example.stcmarvelapp.di

import com.example.stcmarvelapp.data.source.MarvelCharactersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class MarvelCharactersServiceModule {

    @Provides
    @ViewModelScoped
    fun provideCharactersService(retrofit: Retrofit): MarvelCharactersService =
        retrofit.create(MarvelCharactersService::class.java)
}