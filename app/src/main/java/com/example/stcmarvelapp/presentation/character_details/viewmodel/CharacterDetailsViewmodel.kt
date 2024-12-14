package com.example.stcmarvelapp.presentation.character_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stcmarvelapp.core.utils.Response
import com.example.stcmarvelapp.domain.usecase.GetCharacterDetailsUseCase
import com.example.stcmarvelapp.presentation.character_details.view.CharacterDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewmodel @Inject constructor(
    val characterDetailsUseCase: GetCharacterDetailsUseCase
) : ViewModel() {

    private val _characterDetailsUiState = MutableStateFlow<CharacterDetailsUiState?>(null)
    val characterDetailsUiState: StateFlow<CharacterDetailsUiState?> = _characterDetailsUiState

    fun getCharacterDetails(characterId: Int) = viewModelScope.launch(Dispatchers.IO) {
        characterDetailsUseCase(characterId).collect { response ->
            when (response) {
                is Response.Error -> {
                    _characterDetailsUiState.value = CharacterDetailsUiState(
                        error = response.message ?: "Unknown error has occurred"
                    )
                }

                is Response.Loading -> {
                    _characterDetailsUiState.value = CharacterDetailsUiState(isLoading = true)
                }

                is Response.Success -> {
                    _characterDetailsUiState.value =
                        CharacterDetailsUiState(characterDetails = response.data)
                }
            }
        }
    }
}