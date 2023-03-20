package com.example.astonfinalproject.presentation.recyclerView.callback

import androidx.recyclerview.widget.DiffUtil
import com.example.astonfinalproject.domain.Model.EpisodeInfo

class EpisodeDiffCallBack: DiffUtil.ItemCallback<EpisodeInfo>() {
    override fun areItemsTheSame(oldItem: EpisodeInfo, newItem: EpisodeInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EpisodeInfo, newItem: EpisodeInfo): Boolean {
        return oldItem == newItem
    }
}