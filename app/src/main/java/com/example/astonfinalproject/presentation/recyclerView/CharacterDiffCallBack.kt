package com.example.astonfinalproject.presentation.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.example.astonfinalproject.domain.Model.CharacterInfo

class CharacterDiffCallBack: DiffUtil.ItemCallback<CharacterInfo>() {
    override fun areItemsTheSame(oldItem: CharacterInfo, newItem: CharacterInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharacterInfo, newItem: CharacterInfo): Boolean {
        return oldItem == newItem
    }
}