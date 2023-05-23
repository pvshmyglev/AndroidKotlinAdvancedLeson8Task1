package ru.netology.nmedia.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.runtime.ui_view.ViewProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentMapsBinding
import ru.netology.nmedia.dto.MapMarker
import ru.netology.nmedia.dto.MapPoint
import ru.netology.nmedia.viewmodel.MapViewModel


@AndroidEntryPoint
class MapsFragment : Fragment() {

    private val viewModel: MapViewModel by activityViewModels()

    private val textStyle = TextStyle(14F, R.color.blue, 0, TextStyle.Placement.TOP,0F, true, false)

    private val mapInputListener = object : InputListener {
        override fun onMapTap(map: Map, point: Point) {
            val pointClick = MapPoint(point.latitude, point.longitude)
            viewModel.onInstalledPoint(pointClick)
        }
            override fun onMapLongTap(map: Map, point: Point) {
        }
    }

    private val mapTapListenerInMarker = MapObjectTapListener { mapMarker, mapPoint ->
        val viewTap = mapMarker.userData as View

        PopupMenu(viewTap.context, viewTap).apply {
            inflate(R.menu.options_marker)

            setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {

                    R.id.remove_button -> {
                        val idMarker = viewTap.id.toLong()
                        viewModel.onRemoveMarkerById(idMarker)
                        true
                    }
                    R.id.edit_button -> {
                        val idMarker = viewTap.id.toLong()
                        viewModel.onEditMarkerById(idMarker)
                        true
                    }
                    else -> false
                }
            }
        }.show()
        true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentMapsBinding.inflate(inflater, container, false)
        val mapKit = MapKitFactory.getInstance()
        val locationManager = mapKit.createLocationManager()
        mapKit.onStart();
        binding.yandexMapkitMap.onStart();
        val map = binding.yandexMapkitMap.map

        viewModel.loadMarkers()

        viewModel.listMarkers.observe(viewLifecycleOwner) { listMarkers ->
            map.mapObjects.clear()
            listMarkers.map { mapMarker ->
                mapCreateMarker(map,mapMarker)
            }
        }

        context?.let { thisContext ->
            binding.buttonMove.setOnClickListener {

                if (ActivityCompat.checkSelfPermission(
                        thisContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        thisContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ){
                    val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
                    activity?.let{ thisActivity ->
                        ActivityCompat.requestPermissions(thisActivity, permissions,0)
                    }
                } else {
                    locationManager.requestSingleUpdate(object : LocationListener {
                        override fun onLocationUpdated(p0: Location) {
                            val lat = p0.position.latitude
                            val lon = p0.position.longitude
                            mapMove(map, lat, lon)
                        }

                        override fun onLocationStatusUpdated(p0: LocationStatus) {

                        }
                    })
                }
            }

            binding.buttonListMarkers.setOnClickListener {
                findNavController().navigate(R.id.action_nav_maps_fragment_to_listMarkersFragment)
            }
        }

        map.addInputListener(mapInputListener)

        viewModel.mapMarkerChanged.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_nav_maps_fragment_to_nav_edit_map_marker_fragment)
        }

        viewModel.mapPointCurrent.observe(viewLifecycleOwner) { mapMarker ->
            mapMoveImmediately(map, mapMarker.latitude, mapMarker.longitude)
        }

        viewModel.mapMarkerUpdated.observe(viewLifecycleOwner) { mapMarker ->
            viewModel.onMoveToMarker(mapMarker)
        }

        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun mapCreateMarker(map: Map, mapMarker: MapMarker) {
        val targetLocation = Point(mapMarker.latitude, mapMarker.longitude)
        val view = View(requireContext()).apply {
            background = requireContext().getDrawable(R.drawable.ic_baseline_map_point_24)
            id = mapMarker.id.toInt()
            isClickable = true
        }
        val newMapMarker = map.mapObjects.addPlacemark(targetLocation, ViewProvider(view))
        newMapMarker.setTextStyle(textStyle)
        newMapMarker.setText(mapMarker.title)
        newMapMarker.userData = view
        newMapMarker.addTapListener(mapTapListenerInMarker)
    }

    private fun mapMove(map: Map, lat: Double, lon: Double) {
        val targetLocation = Point(lat, lon)
        map.move(
            CameraPosition(targetLocation, 17.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 2f),
            null)
    }

    private fun mapMoveImmediately(map: Map, lat: Double, lon: Double) {
        val targetLocation = Point(lat, lon)
        map.move(
            CameraPosition(targetLocation, 17.0f, 0.0f, 0.0f),
            Animation(Animation.Type.LINEAR, 0f),
            null)
    }

    override fun onStart() {
        super.onStart()
        viewModel.onUpdatePointCurrent()
    }

    companion object {
        fun newInstance() = MapsFragment()
    }

}