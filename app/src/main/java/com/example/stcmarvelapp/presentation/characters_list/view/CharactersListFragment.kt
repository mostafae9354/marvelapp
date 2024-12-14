package com.example.stcmarvelapp.presentation.characters_list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stcmarvelapp.core.Constants
import com.example.stcmarvelapp.core.utils.getQueryTextChangeStateFlow
import com.example.stcmarvelapp.core.utils.hide
import com.example.stcmarvelapp.core.utils.show
import com.example.stcmarvelapp.presentation.characters_list.adapter.CharactersListAdapter
import com.example.stcmarvelapp.presentation.characters_list.viewmodel.CharactersListViewmodel
import com.example.stcmarvelapp.core.utils.PaginationScrollListener
import com.example.stcmarvelapp.R
import com.example.stcmarvelapp.databinding.FragmentCharactersListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersListFragment : Fragment() {

    private lateinit var binding: FragmentCharactersListBinding
    private val viewModel: CharactersListViewmodel by viewModels()
    private lateinit var adapter: CharactersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_characters_list,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addSearchMenu()

        setupUI()
        observeUiState()
    }

    private fun addSearchMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_characters_list_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_search -> {
                        val searchView = menuItem.actionView as SearchView
                        searchView.queryHint = getString(R.string.search_by_name)

                        lifecycleScope.launch {
                            searchView.getQueryTextChangeStateFlow()
                                .debounce(1000)
                                .distinctUntilChanged()
                                .collect { query ->
                                    if (viewModel.searchText != query) {
                                        viewModel.searchText = query
                                        viewModel.isSearchQueryChanged = true
                                        viewModel.offset = 0
                                        viewModel.getCharactersList()
                                    }
                                }
                        }
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    private fun setupUI() {
        adapter = CharactersListAdapter {

            findNavController().navigate(
                R.id.characterDetailsFragment,
                bundleOf(
                    "id" to it.id
                )
            )
        }

        binding.rvCharacters.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCharacters.adapter = adapter

        binding.rvCharacters.addOnScrollListener(object :
            PaginationScrollListener(binding.rvCharacters.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return adapter.itemCount % Constants.LIMIT != 0
            }

            override fun isLoading(): Boolean {
                return viewModel.charactersListUiState.value?.isLoading == true
            }

            override fun loadMoreItems() {
                viewModel.offset += Constants.LIMIT
                viewModel.getCharactersList()
            }
        })
    }

    private fun observeUiState() {

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.charactersListUiState.collect { state ->
                state?.let {

                    if (it.isLoading) {
                        binding.progressBar.show()
                    } else {
                        binding.progressBar.hide()
                    }

                    if (it.charactersList.isNullOrEmpty()) {
                        if (viewModel.isSearchQueryChanged) {
                            binding.rvCharacters.hide()
                        }
                    } else {
                        binding.rvCharacters.show()
                        adapter.submitList(
                            it.charactersList
                        )
                        /*adapter.addItems(
                            it.charactersList,
                            keepOld = !viewModel.isSearchQueryChanged
                        )*/
                        viewModel.isSearchQueryChanged = false
                    }

                    if (!it.error.isNullOrEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            it.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } ?: viewModel.getCharactersList()
            }
        }
    }
}