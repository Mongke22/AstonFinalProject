package com.example.astonfinalproject.presentation.recyclerView.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.astonfinalproject.R
import com.example.astonfinalproject.domain.Model.CharacterInfo
import com.example.astonfinalproject.domain.Model.EpisodeInfo
import com.example.astonfinalproject.presentation.recyclerView.callback.CharacterDiffCallBack
import com.example.astonfinalproject.presentation.recyclerView.callback.EpisodeDiffCallBack
import com.example.astonfinalproject.presentation.recyclerView.holders.CharacterViewHolder
import com.example.astonfinalproject.presentation.recyclerView.holders.EpisodeViewHolder

class EpisodesListAdapter :
    ListAdapter<EpisodeInfo, EpisodeViewHolder>(EpisodeDiffCallBack()) {

    var episodeClickListener: ((EpisodeInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.episodes_card_view, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = getItem(position)

        holder.name.text = episode.name
        holder.number.text = episode.number
        holder.date.text = episode.date

        holder.view.setOnClickListener{
            episodeClickListener?.invoke(episode)
        }
    }
}