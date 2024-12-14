package com.example.stcmarvelapp.presentation.character_details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.stcmarvelapp.R
import com.example.stcmarvelapp.core.utils.hide
import com.example.stcmarvelapp.core.utils.show
import com.example.stcmarvelapp.databinding.FragmentCharacterDetailsBinding
import com.example.stcmarvelapp.domain.entity.MarvelCharacterDetails
import com.example.stcmarvelapp.presentation.character_details.viewmodel.CharacterDetailsViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailsBinding
    private val viewModel: CharacterDetailsViewmodel by viewModels()
    private var characterId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_character_details,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterId = arguments?.getInt("id")
        observeUiState()
    }

    private fun observeUiState() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.characterDetailsUiState.collect { state ->
                state?.let {

                    if (it.isLoading) {
                        binding.progressBar.show()
                    } else {
                        binding.progressBar.hide()
                    }

                    if (it.characterDetails != null) {
                        bindDetails(it.characterDetails)
                    }

                    if (!it.error.isNullOrEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            it.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } ?: viewModel.getCharacterDetails(characterId ?: 0)
            }
        }
    }

    private fun bindDetails(details: MarvelCharacterDetails) {

        binding.llCharacterData.show()
        Glide.with(requireContext())
            .load(details.imageUrl)
            .into(binding.ivCharacterImage)

        binding.tvCharacterName.text = details.name
        binding.tvCharacterDescription.text = details.description
        if (details.description.isEmpty()) {
            binding.tvDescription.hide()
            binding.tvCharacterDescription.hide()
        }
    }
}