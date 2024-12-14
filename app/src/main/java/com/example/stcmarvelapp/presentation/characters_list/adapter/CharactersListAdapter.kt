package com.example.stcmarvelapp.presentation.characters_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stcmarvelapp.databinding.ItemCharacterBinding
import com.example.stcmarvelapp.domain.entity.MarvelCharacter

class CharactersListAdapter(
    private val onCharacterSelected: (MarvelCharacter) -> Unit
) : ListAdapter<MarvelCharacter, CharactersListAdapter.CharacterViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<MarvelCharacter>() {
        override fun areItemsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MarvelCharacter,
            newItem: MarvelCharacter
        ): Boolean {
            return oldItem.name == newItem.name
        }
    }

    class CharacterViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: MarvelCharacter) {
            Glide.with(binding.root.context)
                .load(character.imageUrl)
                .into(binding.ivImage)

            binding.tvName.text = character.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {

        holder.bind(getItem(position))
        holder.binding.root.setOnClickListener {
            onCharacterSelected(getItem(position))
        }
    }

    fun addItems(
        newItems: List<MarvelCharacter>,
        keepOld: Boolean = true
    ) {
        val newCharactersList = mutableListOf<MarvelCharacter>()
        if (keepOld) {
            newCharactersList.addAll(currentList)
        }
        newCharactersList.addAll(newItems)
        submitList(newCharactersList)
    }
}