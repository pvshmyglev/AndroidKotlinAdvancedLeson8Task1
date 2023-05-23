package ru.netology.nmedia.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.dto.MapMarker
import ru.netology.nmedia.entity.MapMarkerEntity

@Dao
interface MapDao {

    @Query("SELECT * FROM markers")
    fun getAllMarkers(): Flow<List<MapMarkerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarker(mapMarker: MapMarkerEntity) : Long

    @Update
    suspend fun updatePost(post: MapMarkerEntity)

    @Query("DELETE FROM markers WHERE id = :id")
    suspend fun removeMapMarkerById(id: Long)


}