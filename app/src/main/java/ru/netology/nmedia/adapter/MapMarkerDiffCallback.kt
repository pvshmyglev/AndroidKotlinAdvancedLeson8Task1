package ru.netology.nmedia.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.netology.nmedia.dto.MapMarker

class MapMarkerDiffCallback: DiffUtil.ItemCallback<MapMarker>() {

    override fun areItemsTheSame(oldItem: MapMarker, newItem: MapMarker): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MapMarker, newItem: MapMarker): Boolean {
        return oldItem == newItem
    }
}