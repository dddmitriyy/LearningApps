package ru.freeit.googlemaps.ui.screens

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.freeit.googlemaps.R
import ru.freeit.googlemaps.databinding.MapFragmentBinding

class GoogleMapFragment : CommonMapFragment() {

    private var googleMap: GoogleMap? = null

    private fun defineLocation() {
        val locationService = LocationServices.getFusedLocationProviderClient(requireContext())
        locationService.lastLocation.addOnSuccessListener { location ->
            val point = LatLng(location.latitude, location.longitude)
            googleMap?.addMarker(MarkerOptions().apply {
                position(point)
                title(getString(R.string.sydney))
            })
            googleMap?.moveCamera(CameraUpdateFactory.newLatLng(point))
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MapFragmentBinding.inflate(inflater, container, false)
        this.mapView = binding.mapView

        binding.mapView.onCreate(savedInstanceState)

        val permissionLocation = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                defineLocation()
            }
        }

        binding.defineLocationButton.setOnClick {
            val permissionGranted = ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (permissionGranted == PackageManager.PERMISSION_GRANTED) {
                defineLocation()
            } else {
                permissionLocation.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        binding.mapView.getMapAsync { googleMap ->
            this.googleMap = googleMap
            val sydney = LatLng(-34.0, 151.0)
            googleMap.addMarker(MarkerOptions().apply {
                position(sydney)
                title(getString(R.string.sydney))
            })
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }

        return binding.root
    }

}