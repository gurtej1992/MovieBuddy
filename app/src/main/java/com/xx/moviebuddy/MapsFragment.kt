package com.xx.moviebuddy

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task


class MapsFragment : Fragment(),OnMapReadyCallback {
    private var myMap: GoogleMap? = null
    var userLocation: Location? = null
    var smf: SupportMapFragment? = null
    private var client: FusedLocationProviderClient? = null
    var latit = 0.0
    var longi = 0.0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        val btnLocate = view.findViewById<Button>(R.id.btn_locate_me)
        smf = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        btnLocate.setOnClickListener {
            Toast.makeText(context,"HEy",Toast.LENGTH_SHORT).show()
        }

        client = LocationServices.getFusedLocationProviderClient(requireActivity())
        if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getCurrentLocation()
        }

        else{
            ActivityCompat.requestPermissions(requireActivity(),arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101)
        }
        return view
    }

    // TO Get current location of user
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val task: Task<Location> = client!!.lastLocation
        task.addOnSuccessListener({ location ->
            if (location != null) {
                userLocation = location as Location?
                smf?.getMapAsync({ googleMap ->
                    myMap = googleMap
                    val latLng = LatLng(location.getLatitude(), location.getLongitude())
                    val options = MarkerOptions()
                        .position(latLng)
                        .title("Here I am")
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9f))
                    googleMap.addMarker(options)
                })
            }
        })
        task.addOnFailureListener(OnFailureListener { e -> Log.d("xx", "" + e.localizedMessage) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
       // mapFragment?.getMapAsync(callback)
    }

    override fun onMapReady(p0: GoogleMap) {
        myMap = p0
        val latLng = LatLng(userLocation!!.latitude, userLocation!!.longitude)
        val options = MarkerOptions()
            .position(latLng)
            .title("Here I am")
        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9f))
        p0.addMarker(options)
    }
    private val callback = OnMapReadyCallback { googleMap ->
        val latLng = LatLng(userLocation!!.latitude, userLocation!!.longitude)
        googleMap.addMarker(MarkerOptions().position(latLng).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
    }
}