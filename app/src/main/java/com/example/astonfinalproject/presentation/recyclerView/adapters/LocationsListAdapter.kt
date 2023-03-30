package com.example.astonfinalproject.presentation.recyclerView.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.astonfinalproject.R
import com.example.astonfinalproject.domain.Model.LocationInfo
import com.example.astonfinalproject.presentation.recyclerView.DiffCallBack
import com.example.astonfinalproject.presentation.recyclerView.holders.LocationViewHolder
import javax.inject.Inject

class LocationsListAdapter @Inject constructor() :
    ListAdapter<LocationInfo, LocationViewHolder>(DiffCallBack<LocationInfo>()) {

    var locationClickListener: ((LocationInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.locations_card_view, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)

        holder.name.text = location.name
        holder.type.text = location.type
        holder.dimension.text = location.dimension

        holder.view.setOnClickListener {
            locationClickListener?.invoke(location)
        }
    }
}