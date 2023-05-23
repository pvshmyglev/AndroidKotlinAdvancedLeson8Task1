package ru.netology.nmedia.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.netology.nmedia.dao.MapDao
import ru.netology.nmedia.entity.MapMarkerEntity

@Database(entities = [MapMarkerEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun mapDao(): MapDao
}