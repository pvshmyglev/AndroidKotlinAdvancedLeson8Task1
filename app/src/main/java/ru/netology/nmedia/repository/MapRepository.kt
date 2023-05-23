package ru.netology.nmedia.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.dto.MapMarker

interface MapRepository {

    val listMarkers : Flow<List<MapMarker>>
    suspend fun saveMarker(marker: MapMarker)
    suspend fun removeMarker(id: Long)
    suspend fun getAll()

}