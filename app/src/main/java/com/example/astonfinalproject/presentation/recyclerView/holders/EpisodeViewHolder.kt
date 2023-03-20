package com.example.astonfinalproject.presentation.recyclerView.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.astonfinalproject.R

class EpisodeViewHolder(
    val view: View
) : RecyclerView.ViewHolder(view) {
    var name: TextView = view.findViewById(R.id.tvEpisodeName)
    var number: TextView = view.findViewById(R.id.tvEpisodeNumber)
    var date: TextView = view.findViewById(R.id.tvEpisodeDate)
}