package com.example.zadanie.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.zadanie.R
import com.example.zadanie.widgets.BottomBar
import com.mapbox.maps.Style
import com.example.zadanie.databinding.FragmentMapBinding

class MapFragment : Fragment(R.layout.fragment_map) {
    private var binding: FragmentMapBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentMapBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
        }.also { bnd ->
            bnd.bottomBar.setActive(BottomBar.MAP)
            bnd.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
        }
    }
}