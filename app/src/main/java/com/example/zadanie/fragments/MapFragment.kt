package com.example.zadanie.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.zadanie.R
import com.example.zadanie.widgets.BottomBar
import com.mapbox.maps.Style
import com.example.zadanie.databinding.FragmentMapBinding
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location

class MapFragment : Fragment(R.layout.fragment_map) {
    private lateinit var binding: FragmentMapBinding
    private var selectedPoint: CircleAnnotation? = null
    private var lastLocation: Point? = null
    private lateinit var annotationManager: CircleAnnotationManager
    private val PERMISSIONS_REQUIRED = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }

        else -> {
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {

        }

    fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }.also { bnd ->
            bnd.bottomBar.setActive(BottomBar.MAP)

            annotationManager = bnd.mapView.annotations.createCircleAnnotationManager()

            val hasPermission = hasPermissions(requireContext())
            onMapReady(hasPermission)

            bnd.myLocation.setOnClickListener {
                if (!hasPermissions(requireContext())) {
                    requestPermissionLauncher.launch(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                    )
                } else {
                    lastLocation?.let { refreshLocation(it) }
                    addLocationListeners()
                    Log.d("MapFragment","location click")
                }
            }
        }
    }

    private fun onMapReady(enabled: Boolean) {
        binding.mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(14.3539484, 49.8001304))
                .zoom(2.0)
                .build()
        )
        binding.mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            if (enabled) {
                initLocationComponent()
                addLocationListeners()
            }
        }

        binding.mapView.getMapboxMap().addOnMapClickListener {
            if (hasPermissions(requireContext())) {
                onCameraTrackingDismissed()
            }
            true
        }
    }

    private fun initLocationComponent() {
        Log.d("MapFragment","initLocationComponent")
        val locationComponentPlugin = binding.mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.pulsingEnabled = true
        }
    }

    private fun addLocationListeners() {
        Log.d("MapFragment","addLocationListeners")
        binding.mapView.location.addOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )
        binding.mapView.gestures.addOnMoveListener(onMoveListener)
    }
    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        Log.d("MapFragment","poloha je $it")
        refreshLocation(it)
    }

    private fun refreshLocation(point: Point) {
        binding.mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(point).zoom(14.0).build())
        binding.mapView.gestures.focalPoint = binding.mapView.getMapboxMap().pixelForCoordinate(point)
        lastLocation = point
        addMarker(point)
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    private fun onCameraTrackingDismissed() {
        binding.mapView.apply {
            location.removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
            gestures.removeOnMoveListener(onMoveListener)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.apply {
            location.removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
            gestures.removeOnMoveListener(onMoveListener)
        }
    }

    private fun addMarker(point: Point) {

        if (selectedPoint == null) {
            annotationManager.deleteAll()
            val pointAnnotationOptions = CircleAnnotationOptions()
                .withPoint(point)
                .withCircleRadius(100.0)
                .withCircleOpacity(0.2)
                .withCircleColor("#000")
                .withCircleStrokeWidth(2.0)
                .withCircleStrokeColor("#ffffff")
            selectedPoint = annotationManager.create(pointAnnotationOptions)
        } else {
            selectedPoint?.let {
                it.point = point
                annotationManager.update(it)
            }
        }
    }
}