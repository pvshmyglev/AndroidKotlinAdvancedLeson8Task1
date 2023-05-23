package ru.netology.nmedia.viewmodel

import ru.netology.nmedia.dto.MapMarker
import ru.netology.nmedia.dto.MapPoint

interface MapCommands {

    fun onSaveMarker(markerData: MapMarker)
    fun onRemoveMarker(marker: MapMarker)
    fun onRemoveMarkerById(id: Long)
    fun onInstalledPoint(point: MapPoint)
    fun onEditMarker(marker: MapMarker)
    fun onEditMarkerById(id: Long)
    fun onMoveToMarker(marker: MapMarker)
    fun onUpdatePointCurrent()
    fun loadMarkers()

}
