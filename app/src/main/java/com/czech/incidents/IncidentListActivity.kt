package com.czech.incidents

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.czech.incidents.databinding.ActivityIncidentsListBinding

class IncidentListActivity: DialogFragment() {

    private lateinit var alertDialog: AlertDialog

    companion object {
        const val TAG = "IncidentListActivity"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val incidentListView: View = layoutInflater.inflate(R.layout.activity_incidents_list, null)

        val incidentList = incidentListView.findViewById<RadioGroup>(R.id.incident_list)

        val args = arguments
        val getCoord = args?.getString("coordinates")

        fun toDesc(type: String) {

            this.dismiss()

            val bundle = Bundle()
            bundle.putString("coordinates", getCoord)
            bundle.putString("incidentType", type)
            val descDialog = IncidentDescActivity()
            descDialog.arguments = bundle
            descDialog.show(parentFragmentManager, "TAG")
        }

        Toast.makeText(requireContext(), getCoord, Toast.LENGTH_LONG).show()

        val cancelBtn = incidentListView.findViewById<Button>(R.id.cancel_btn)
        cancelBtn.setOnClickListener {
            alertDialog.dismiss()
        }

        val nextBtn = incidentListView.findViewById<Button>(R.id.next_btn)
        nextBtn.setOnClickListener {

            when (incidentList.checkedRadioButtonId) {

                -1 -> {
                    Toast.makeText(requireContext(), "Please select an incident type", Toast.LENGTH_LONG).show()
                }

                R.id.accident_rb -> {
                    Toast.makeText(requireContext(), "Accident selected", Toast.LENGTH_LONG).show()

                    toDesc("accident")
                }

                R.id.road_block_rb -> {
                    Toast.makeText(requireContext(), "Road Block selected", Toast.LENGTH_LONG).show()

                    this.dismiss()

                    toDesc("road block")
                }

                R.id.crime_rb -> {
                    Toast.makeText(requireContext(), "Crime/Theft selected", Toast.LENGTH_LONG).show()

                    toDesc("crime/theft")
                }

                R.id.construction_rb -> {
                    Toast.makeText(requireContext(), "Road Construction selected", Toast.LENGTH_LONG).show()

                    toDesc("road construction")
                }

                R.id.fire_rb -> {
                    Toast.makeText(requireContext(), "Fire selected", Toast.LENGTH_LONG).show()

                    toDesc("fire")
                }

                R.id.others_rb -> {
                    Toast.makeText(requireContext(), "Others selected", Toast.LENGTH_LONG).show()

                    toDesc("others")
                }
            }
        }

        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(incidentListView)
        alertDialog = dialogBuilder.create()

        return alertDialog

    }
}