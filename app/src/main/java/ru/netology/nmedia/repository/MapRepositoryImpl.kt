package ru.netology.nmedia.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.nmedia.dao.MapDao
import ru.netology.nmedia.dto.MapMarker
import ru.netology.nmedia.entity.MapMarkerEntity
import ru.netology.nmedia.entity.toMapMarker
import ru.netology.nmedia.entity.toMapMarkerEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapRepositoryImpl @Inject constructor(private val mapDao: MapDao) : MapRepository {

    override val listMarkers = mapDao.getAllMarkers()
        .map(List<MapMarkerEntity>::toMapMarker)
        .flowOn(Dispatchers.Default)

    override suspend fun saveMarker(marker: MapMarker) {
        mapDao.insertMarker(marker.toMapMarkerEntity())
    }

    override suspend fun removeMarker(id: Long) {
        mapDao.removeMapMarkerById(id)
    }

    override suspend fun getAll() {
        mapDao.getAllMarkers()
    }

}