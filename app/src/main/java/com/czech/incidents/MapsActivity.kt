package com.czech.incidents

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.czech.incidents.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Float.parseFloat
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val latitude = 6.46021
        val longitude =


        val zoomLevel = 15f

        val baseLocation = LatLng(latitude, longitude)

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(baseLocation, zoomLevel))

        onMapClick()
    }

    private fun onMapClick() {

        map.setOnMapClickListener { latLng ->

            val latLong = String.format(
                Locale.getDefault(),
                "%1\$.5f, %2\$.5f",
                latLng.latitude,
                latLng.longitude
            )

            showIncidentListDialog(latLong)

//            map.addMarker(
//                MarkerOptions()
//                    .position(latLng)
//                    .title(getString(R.string.added_pin))
//                    .snippet(info)
//            )

        }
    }

    private fun showIncidentListDialog(coordinates: String) {

        val listDialog = IncidentListActivity()
        val bundle = Bundle()
        bundle.putString("coordinates", coordinates)
        listDialog.arguments = bundle
        listDialog.show(supportFragmentManager, "TAG")

    }
}