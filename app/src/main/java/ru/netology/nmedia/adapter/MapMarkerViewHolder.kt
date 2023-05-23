package ru.netology.nmedia.adapter


import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardMarkerBinding
import ru.netology.nmedia.dto.MapMarker
import ru.netology.nmedia.viewmodel.MapCommands

class MapMarkerViewHolder (private val binding: CardMarkerBinding, private val interactiveCommands: MapCommands) : RecyclerView.ViewHolder(binding.root){

    fun bind (marker: MapMarker) = with(binding) {
        textLatitude.text = marker.latitude.toString()
        textLongitude.text = marker.longitude.toString()
        textTitle.text = marker.title
        textDescription.text = marker.description

        imageButtonMore.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.options_marker)

                setOnMenuItemClickListener { menuItem ->
                    when(menuItem.itemId) {
                        R.id.remove_button -> {
                            interactiveCommands.onRemoveMarker(marker)
                            true
                        }
                        R.id.edit_button -> {
                            interactiveCommands.onEditMarker(marker)
                            true
                        }
                        else -> false
                    }
                }

            }.show()
        }

        mapMarkerConstraintLayout.setOnClickListener {

            interactiveCommands.onMoveToMarker(marker)

        }

    }


}
