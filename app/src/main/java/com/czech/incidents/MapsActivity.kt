package com.czech.incidents
//
//import android.app.Activity
//import android.content.Context
//import android.graphics.*
//import android.os.Bundle
//import android.util.DisplayMetrics
//import android.view.View
//import android.view.ViewGroup
//import androidx.appcompat.app.AppCompatActivity
//import com.czech.incidents.databinding.ActivityMapsBinding
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.BitmapDescriptorFactory
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.Marker
//import com.google.android.gms.maps.model.MarkerOptions
//import com.google.firebase.database.*
//import de.hdodenhof.circleimageview.CircleImageView
//import java.util.*
//
//
//class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
//
//    private lateinit var map: GoogleMap
//    private lateinit var binding: ActivityMapsBinding
//
//    private lateinit var firebaseDatabase: DatabaseReference
//
//    var storedMarkers: Map<String, Marker> = HashMap<String, Marker>()
//
//    private fun createCustomMarker(context: Context, icon: Int): Bitmap {
//        val marker: View = layoutInflater.inflate(R.layout.pin_layout, null)
//
//        val markerImage = marker.findViewById(R.id.user_dp) as CircleImageView
//        markerImage.setImageResource(icon)
//        val displayMetrics = DisplayMetrics()
//        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
//        ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT)
//            .also { marker.layoutParams = it }
//        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
//        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
//        marker.buildDrawingCache()
//        val bitmap = Bitmap.createBitmap(
//            marker.measuredWidth,
//            marker.measuredHeight,
//            Bitmap.Config.ARGB_8888
//        )
//        val canvas = Canvas(bitmap)
//        marker.draw(canvas)
//        return bitmap
//    }
//
//    fun addMarker(latLng: LatLng, description: String, type: String, icon: Int) {
//
//        map.addMarker(
//            MarkerOptions()
//                .position(latLng)
//                .title(type.uppercase(Locale.getDefault()))
//                .snippet(description)
//                .icon(BitmapDescriptorFactory.fromBitmap(
//                    createCustomMarker(this, icon)
//                ))
//        )
//
//    }
//
//    fun removeMarkers(snapshot: DataSnapshot) {
//        val id = snapshot.key
//
//        if (storedMarkers.containsKey(id)) {
//
//            val marker = storedMarkers[id]
//            marker?.remove()
//        }
//    }
//
//    var incidentUpdateListener = object : ChildEventListener {
//
//        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//            val id = snapshot.key
//            val coord = snapshot.child("coordinates").value.toString()
//            val type = snapshot.child("incidentType").value.toString()
//            val description = snapshot.child("description").value.toString()
//
//            val stringLatLng = coord.split(',')
//            val latitude = stringLatLng[0].toDouble()
//            val longitude = stringLatLng[1].toDouble()
//
//            val latLng = LatLng(latitude, longitude)
//
//            val marker = storedMarkers[id]
//
//            if (marker != null) {
//                marker.position = latLng
//            }
//
//            when (type) {
//
//                "accident" -> {
//                    addMarker(latLng, description, type, R.drawable.accident)
//                }
//
//                "road block" -> {
//                    addMarker(latLng, description, type, R.drawable.road_block)
//                }
//
//                "crime/theft" -> {
//                    addMarker(latLng, description, type, R.drawable.crime)
//                }
//
//                "road construction" -> {
//                    addMarker(latLng, description, type, R.drawable.road_construction)
//                }
//
//                "fire" -> {
//                    addMarker(latLng, description, type, R.drawable.fire)
//                }
//
//                "others" -> {
//                    addMarker(latLng, description, type, R.drawable.other)
//                }
//            }
//        }
//
//        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//            TODO("Not yet implemented")
//        }
//
//        override fun onChildRemoved(snapshot: DataSnapshot) {
//            removeMarkers(snapshot)
//
//        }
//
//        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//            TODO("Not yet implemented")
//        }
//
//        override fun onCancelled(error: DatabaseError) {
//            TODO("Not yet implemented")
//        }
//
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMapsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        map = googleMap
//        firebaseDatabase = FirebaseDatabase.getInstance().reference.child("Incidents")
//
//        firebaseDatabase.addChildEventListener(incidentUpdateListener)
//
//        val latitude = 6.46021
//        val longitude = 7.49619
//        val zoomLevel = 15f
//
//        val baseLocation = LatLng(latitude, longitude)
//
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(baseLocation, zoomLevel))
//
//        onMapClick()
//    }
//
//    private fun onMapClick() {
//
//        map.setOnMapClickListener { latLng ->
//
//            val latLong = String.format(
//                Locale.getDefault(),
//                "%1\$.5f, %2\$.5f",
//                latLng.latitude,
//                latLng.longitude
//            )
//
//            showIncidentListDialog(latLong)
//
//        }
//    }
//
//    private fun showIncidentListDialog(coordinates: String) {
//
//        val listDialog = IncidentListActivity()
//        val bundle = Bundle()
//        bundle.putString("coordinates", coordinates)
//        listDialog.arguments = bundle
//        listDialog.show(supportFragmentManager, "TAG")
//
//    }
//}