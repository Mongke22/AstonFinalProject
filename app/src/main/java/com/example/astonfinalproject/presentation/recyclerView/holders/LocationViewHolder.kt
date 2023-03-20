package com.example.astonfinalproject.presentation.recyclerView.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.astonfinalproject.R

class LocationViewHolder(
    val view: View
) : RecyclerView.ViewHolder(view) {
    var name: TextView = view.findViewById(R.id.tvEpisodeNameLocation)
    var type: TextView = view.findViewById(R.id.tvType)
    var dimension: TextView = view.findViewById(R.id.tvDimension)
}