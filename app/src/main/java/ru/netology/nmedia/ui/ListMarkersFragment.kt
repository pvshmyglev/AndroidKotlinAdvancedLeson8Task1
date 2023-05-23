package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.MapMarkerAdapter
import ru.netology.nmedia.databinding.FragmentListMaerkersBinding
import ru.netology.nmedia.viewmodel.MapViewModel

@AndroidEntryPoint
class ListMarkersFragment : Fragment() {

    private val viewModel: MapViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentListMaerkersBinding.inflate(inflater, container, false)
        val adapter =  MapMarkerAdapter(viewModel)
        binding.listOfMarkers.adapter = adapter

        viewModel.listMarkers.observe(viewLifecycleOwner) { listMarkers ->
            adapter.submitList(listMarkers)
        }

        viewModel.mapMarkerChanged.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_nav_list_markers_fragment_to_edit_map_marker_fragment)
        }

        viewModel.mapPointCurrent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_nav_list_markers_fragment_to_maps_fragment)
        }

        return binding.root
    }

    companion object {
        fun newInstance() = ListMarkersFragment()
    }

}