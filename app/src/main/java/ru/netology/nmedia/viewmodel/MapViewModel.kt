package ru.netology.nmedia.viewmodel


import androidx.lifecycle.*
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.netology.nmedia.dto.MapMarker
import ru.netology.nmedia.dto.MapPoint
import ru.netology.nmedia.repository.MapRepository
import ru.netology.nmedia.util.SingleLiveEvent
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MapViewModel @Inject constructor (
    private val repository: MapRepository,
) : ViewModel(), MapCommands {

    val listMarkers : LiveData<List<MapMarker>> =
        repository.listMarkers.asLiveData(Dispatchers.Default)

    private val _mapPointCurrent = SingleLiveEvent<MapPoint>()
    val mapPointCurrent: LiveData<MapPoint>
        get() = _mapPointCurrent

    private val _mapMarkerChanged = SingleLiveEvent<MapMarker>()
    val mapMarkerChanged: LiveData<MapMarker>
        get() = _mapMarkerChanged

    private val _mapMarkerUpdated = SingleLiveEvent<MapMarker>()
    val mapMarkerUpdated: LiveData<MapMarker>
        get() = _mapMarkerUpdated

    private val emptyMarker = MapMarker(
        0,
        0.0,
        0.0,
        "",
        ""
    )
    val editedMarker = MutableLiveData(emptyMarker)

    override fun onSaveMarker(markerData: MapMarker) {
        viewModelScope.launch {
            editedMarker.value?.let { thisEditedPost ->

                    val markerForSaved = thisEditedPost.copy(
                        latitude = markerData.latitude,
                        longitude = markerData.longitude,
                        title = markerData.title,
                        description = markerData.description
                    )
                    repository.saveMarker(markerForSaved)
                    _mapMarkerUpdated.setValue(markerForSaved)
                }
            editedMarker.value = emptyMarker
            }

    }

    override fun onInstalledPoint(point: MapPoint) {
        val newMarker = MapMarker(0, point.latitude, point.longitude, "", "")
        editedMarker.value = newMarker
        _mapMarkerChanged.setValue(newMarker)
    }

    override fun onEditMarker(marker: MapMarker) {
        editedMarker.value = marker
        _mapMarkerChanged.setValue(marker)
    }

    override fun onEditMarkerById(id: Long) {
        listMarkers.value?.map { marker ->
            if (marker.id == id) {
                editedMarker.value = marker
                _mapMarkerChanged.setValue(marker)
            }
        }
    }

    override fun onMoveToMarker(marker: MapMarker) {
        val pointOnMarker = MapPoint(marker.latitude, marker.longitude)
        _mapPointCurrent.setValue(pointOnMarker)
    }

    override fun onRemoveMarker(marker: MapMarker) {
        viewModelScope.launch {
            try {
                repository.removeMarker(marker.id)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    override fun onRemoveMarkerById(id: Long) {

        listMarkers.value?.map { marker ->
            if (marker.id == id) {
                viewModelScope.launch {
                    try {
                        repository.removeMarker(marker.id)
                    } catch (e: Exception) {
                        println(e)
                    }
                }
            }
        }

    }

    override fun onUpdatePointCurrent() {
        if (_mapPointCurrent.value == null)
        {
            _mapMarkerChanged.value?.let {
                _mapPointCurrent.setValue(MapPoint(it.latitude, it.longitude))
            }
        } else {
            _mapPointCurrent.value?.let {
                _mapPointCurrent.setValue(MapPoint(it.latitude, it.longitude))
            }
        }
    }

    override fun loadMarkers() {
        viewModelScope.launch {
                try {
                    repository.getAll()
                } catch (e: Exception) {
                    println(e)
               }
        }
    }

}

