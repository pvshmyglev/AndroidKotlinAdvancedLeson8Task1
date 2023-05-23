package ru.netology.nmedia.entity

import ru.netology.nmedia.dto.MapMarker

internal fun List<MapMarkerEntity>.toMapMarker() = map(MapMarkerEntity::toMapMarker)
internal fun List<MapMarker>.toMapMarkerEntity() = map(MapMarker::toMapMarkerEntity)


internal fun MapMarkerEntity.toMapMarker() = MapMarker (

    id = id,
    latitude = latitude,
    longitude = longitude,
    title = title,
    description = description,
)

internal fun MapMarker.toMapMarkerEntity() = MapMarkerEntity (

    id = id,
    latitude = latitude,
    longitude = longitude,
    title = title,
    description = description,

)