package ru.netology.nmedia.dto

data class MapMarker(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val title: String = "",
    val description: String = "",
)
