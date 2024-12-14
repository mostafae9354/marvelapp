package com.example.stcmarvelapp.presentation.characters_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stcmarvelapp.core.utils.Response
import com.example.stcmarvelapp.domain.entity.MarvelCharacter
import com.example.stcmarvelapp.domain.usecase.GetCharactersListUseCase
import com.example.stcmarvelapp.presentation.characters_list.view.CharactersListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersListViewmodel @Inject constructor(
    private val charactersListUseCase: GetCharactersListUseCase
) : ViewModel() {

    private val _charactersListUiState = MutableStateFlow<CharactersListUiState?>(null)
    val charactersListUiState: StateFlow<CharactersListUiState?> = _charactersListUiState

    private val currentList: MutableList<MarvelCharacter> = mutableListOf()
    var searchText: String = ""
    var offset = 0

    fun getCharactersList() = viewModelScope.launch(Dispatchers.IO) {
        charactersListUseCase(offset, searchText.takeIf {
            it.isNotEmpty()
        }).collect { response ->
            when (response) {

                is Response.Loading -> {
                    _charactersListUiState.value = CharactersListUiState(isLoading = true)
                }

                is Response.Error -> {
                    _charactersListUiState.value = CharactersListUiState(
                        error = response.message ?: "Unknown error has occurred"
                    )
                }

                is Response.Success -> {
                    if (isFirstPage()) {
                        currentList.clear()
                    }
                    currentList.addAll(response.data ?: listOf())
                    _charactersListUiState.value =
                        CharactersListUiState(charactersList = currentList.toList())
                }
            }
        }
    }

    fun isFirstPage() = offset == 0
}