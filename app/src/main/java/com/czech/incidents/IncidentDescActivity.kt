package com.czech.incidents

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.czech.incidents.databinding.ActivityIncidentsDescBinding
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.*

private const val INCIDENTS_REFS = "Incidents"

class IncidentDescActivity: DialogFragment() {

    private lateinit var alertDialog: AlertDialog
    private lateinit var binding: ActivityIncidentsDescBinding

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = ActivityIncidentsDescBinding.inflate(inflater)

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val incidentDescView: View = layoutInflater.inflate(R.layout.activity_incidents_desc, null)

        val desc = incidentDescView.findViewById<EditText>(R.id.description)

        val cancelBtn = incidentDescView.findViewById<Button>(R.id.cancel_btn)
        cancelBtn.setOnClickListener {
            alertDialog.dismiss()
        }

        val type = incidentDescView.findViewById<TextView>(R.id.incident_type)
        val coord = incidentDescView.findViewById<TextView>(R.id.coord)

        val args = arguments

        type.text = args?.getString("incidentType")
        coord.text = args?.getString("coordinates")

        val reportBtn = incidentDescView.findViewById<Button>(R.id.report_btn)
        reportBtn.setOnClickListener {
            if (desc.text.isNotEmpty()) {

                val incidentType = args?.getString("incidentType")

                val getCoordString = args?.getString("coordinates")
                val latLong = getCoordString?.split(',')
                val latitude = latLong!![0].toDouble()
                val longitude = latLong[1].toDouble()

                val incidentCoord = LatLng(latitude, longitude)

                val description = desc.text.toString()

                firebaseDatabase = FirebaseDatabase.getInstance()
                databaseReference = firebaseDatabase.getReference(INCIDENTS_REFS)

                val incidents = Incidents(
                    incidentType,
                    incidentCoord,
                    description
                )

                    databaseReference.setValue(incidents)
                        .addOnSuccessListener {
                            desc.text.clear()

                            Toast.makeText(requireContext(), "Reported incident: $id", Toast.LENGTH_LONG).show()

                            alertDialog.dismiss()
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), "Failed to report: $it. Please try again", Toast.LENGTH_LONG).show()

                            Log.d("TAG", it.toString())
                        }

            } else {

                Toast.makeText(requireContext(), "Please enter incident description", Toast.LENGTH_LONG).show()
            }
        }

        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(incidentDescView)
        alertDialog = dialogBuilder.create()

        return alertDialog
    }
}