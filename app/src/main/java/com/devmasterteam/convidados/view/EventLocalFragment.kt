package com.devmasterteam.convidados.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.devmasterteam.convidados.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class EventLocalFragment : Fragment() {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private var selectedMarkerLocation: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_local, container, false)

        mapView = rootView.findViewById(R.id.mapView)

        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync { gMap ->
            googleMap = gMap

            // Define um ouvinte para capturar a localização quando um marcador é clicado
            googleMap.setOnMarkerClickListener { marker ->
                selectedMarkerLocation = marker.position
                true // Retorna true para consumir o evento de clique no marcador
            }
        }

        val shareButton = rootView.findViewById<Button>(R.id.share_btn)
        shareButton.setOnClickListener {
            // Verifique se há um marcador selecionado
            if (selectedMarkerLocation != null) {
                // Crie um link do Google Maps com a localização do marcador
                val latitude = -25.445610928246527
                val longitude = -49.35947604173683
                val mapUri = Uri.parse("geo:$latitude,$longitude")
                val intent = Intent(Intent.ACTION_VIEW, mapUri)
                intent.setPackage("com.google.android.apps.maps") // Abra no Google Maps
                startActivity(intent)
                Log.d("ShareLocation", "Button clicked")
            }
        }

        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(requireActivity())
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
