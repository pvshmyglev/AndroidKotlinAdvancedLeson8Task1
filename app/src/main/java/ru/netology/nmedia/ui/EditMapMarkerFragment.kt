package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentEditMapMarkerBinding
import ru.netology.nmedia.dto.MapMarker
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.viewmodel.MapViewModel

@AndroidEntryPoint
class EditMapMarkerFragment : Fragment() {

    private val viewModel: MapViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val menuHost: MenuHost = requireActivity()

        val binding = FragmentEditMapMarkerBinding.inflate(inflater, container, false)
        binding.textTitle.setText(viewModel.mapMarkerChanged.value?.title)
        binding.textDescription.setText(viewModel.mapMarkerChanged.value?.description)
        //binding.textLatitude.setText(String.format("%.4f", viewModel.mapMarkerUpdated.value?.latitude))
        //binding.textLongitude.setText(String.format("%.4f", viewModel.mapMarkerUpdated.value?.longitude))
        binding.textLatitude.setText(viewModel.mapMarkerChanged.value?.latitude.toString())
        binding.textLongitude.setText(viewModel.mapMarkerChanged.value?.longitude.toString())


        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.options_edit_marker, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.item_new_post_save -> {

                        if (binding.textTitle.text.isNotBlank()) {
                            val latitude = binding.textLatitude.text.toString().toDouble()
                            val longitude = binding.textLongitude.text.toString().toDouble()
                            val title = binding.textTitle.text.toString()
                            val description = binding.textDescription.text.toString()
                            val markerData = MapMarker(0, latitude, longitude, title, description)
                            viewModel.onSaveMarker(markerData)
                            requireView().hideKeyboard()
                        } else {
                            showToastMessage("title was not set. Point not created!")
                        }
                        findNavController().navigateUp()

                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    private fun showToastMessage(text: String) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    companion object {
        fun newInstance() = EditMapMarkerFragment()
    }



}