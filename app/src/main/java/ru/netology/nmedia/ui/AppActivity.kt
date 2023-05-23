package ru.netology.nmedia.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.R

@AndroidEntryPoint
class AppActivity : AppCompatActivity(R.layout.activity_app) {
    override fun onCreate(savedInstanceState: Bundle?) {
        val mapApiKey = BuildConfig.MAPS_API_KEY
        MapKitFactory.setLocale("Ru_Ru")
        MapKitFactory.setApiKey(mapApiKey)
        MapKitFactory.initialize(this)

        super.onCreate(savedInstanceState)
    }
}