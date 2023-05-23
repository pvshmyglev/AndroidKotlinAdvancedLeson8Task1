package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.netology.nmedia.databinding.CardMarkerBinding
import ru.netology.nmedia.dto.MapMarker
import ru.netology.nmedia.viewmodel.MapCommands

class MapMarkerAdapter(private val interactionCommands: MapCommands) : ListAdapter<MapMarker, MapMarkerViewHolder>(
    MapMarkerDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapMarkerViewHolder {
        val binding = CardMarkerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MapMarkerViewHolder(binding, interactionCommands)
    }

    override fun onBindViewHolder(holder: MapMarkerViewHolder, position: Int) {

        holder.bind( getItem(position) )

    }

}