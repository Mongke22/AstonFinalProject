package com.example.astonfinalproject.presentation.recyclerView.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.astonfinalproject.R
import com.example.astonfinalproject.domain.Model.CharacterInfo
import com.example.astonfinalproject.presentation.recyclerView.DiffCallBack
import com.example.astonfinalproject.presentation.recyclerView.holders.CharacterViewHolder
import javax.inject.Inject

class CharactersListAdapter @Inject constructor() :
    ListAdapter<CharacterInfo, CharacterViewHolder>(DiffCallBack<CharacterInfo>()) {

    var characterClickListener: ((CharacterInfo) -> Unit)? = null
    var characterSavePictureFunc: ((Int, String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.characters_card_view, parent, false)
        return CharacterViewHolder(view, characterSavePictureFunc)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.setGender(character.gender)
        holder.setImage(character.imgSrc, character.imgUrl, character.id)
        holder.setName(character.name)
        holder.setSpecies(character.species)
        holder.setStatus(character.status)
        holder.view.setOnClickListener {
            characterClickListener?.invoke(character)
        }
    }

    override fun submitList(list: List<CharacterInfo>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}