package com.example.smokedrop.map

import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.smokedrop.R
import com.example.smokedrop.databinding.FragmentMapBinding
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var viewModel: ViewModelMap

    private lateinit var map: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_map,
            container,
            false
        )
        Log.i("TimeFragment", "Called ViewModelProvider.get")

        viewModel = ViewModelProvider(this).get(ViewModelMap::class.java)
        // Set the viewmodel for databinding - this allows the bound layout access
        // to all the data in the VieWModel
        binding.viewModelTime = viewModel
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Find and start map parameters
        getInstance().load(activity, PreferenceManager.getDefaultSharedPreferences(activity))
        map = requireView().findViewById(R.id.mapview)
        map.setTileSource(TileSourceFactory.HIKEBIKEMAP)
        map.setMultiTouchControls(true)
        val mapController = map.controller

        //Min Max zoom set
        mapController.setZoom(15.0)
        viewModel.geoPointNow.observe(viewLifecycleOwner, {
            mapController.setCenter(it)
        })
        //setFilter mb livedata?
        map.overlayManager.tilesOverlay.setColorFilter(filterMaps())

        var pois = ArrayList<SmokePlace>()
        viewModel.pois.observe(viewLifecycleOwner, {
            pois = it
            map.overlays.add(markerMaps(pois))
        })
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }


    private fun markerMaps(pois: ArrayList<SmokePlace>): RadiusMarkerClusterer {
        val poiMarkers = RadiusMarkerClusterer(activity)
        println("I'm from FUNCTION")
        for (poi in pois) {
            println("I'm from FOR")
            val poiMarker = Marker(map)
            poiMarker.position = poi.mLocation
            poiMarker.title = poi.mTime
            poiMarker.icon = activity?.let { ContextCompat.getDrawable(it, R.drawable.location_pin) }
            poiMarkers.add(poiMarker)
            poiMarker.infoWindow = MarkerInfoBubble(R.layout.bubble_maket, map)
        }
        poiMarkers.setIcon(activity?.let { AppCompatResources.getDrawable(it, R.drawable.data_usage)?.toBitmap(100,100) })
        poiMarkers.setRadius(300)
        println("I'm RETURN")
        return poiMarkers
}

    // Applying a light filter to a Maps
    private fun filterMaps():ColorMatrixColorFilter {
        val inverseMatrix = ColorMatrix(
            floatArrayOf(
                -1.0f, 0.0f, 0.0f, 0.0f, 255f,
                0.0f, -1.0f, 0.0f, 0.0f, 255f,
                0.0f, 0.0f, -1.0f, 0.0f, 255f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f
            )
        )
        val destinationColor: Int = Color.parseColor("#FF2A2A2A")
        val lr: Float = (255.0f - Color.red(destinationColor)) / 255.0f
        val lg: Float = (255.0f - Color.green(destinationColor)) / 255.0f
        val lb: Float = (255.0f - Color.blue(destinationColor)) / 255.0f
        val grayscaleMatrix = ColorMatrix(
            floatArrayOf(
                lr, lg, lb, 0f, 0f,  //
                lr, lg, lb, 0f, 0f,  //
                lr, lg, lb, 0f, 0f, 0f, 0f, 0f, 0f, 255f
            )
        )
        grayscaleMatrix.preConcat(inverseMatrix)
        val dr: Int = Color.red(destinationColor)
        val dg: Int = Color.green(destinationColor)
        val db: Int = Color.blue(destinationColor)
        val drf = dr / 255f
        val dgf = dg / 255f
        val dbf = db / 255f
        val tintMatrix = ColorMatrix(
            floatArrayOf(
                drf, 0f, 0f, 0f, 0f, 0f,
                dgf, 0f, 0f, 0f, 0f, 0f,
                dbf, 0f, 3f, 0.5f, 0f, 1f, 1f, 0f
            )
        )
        tintMatrix.preConcat(grayscaleMatrix)
        val lDestination = drf * lr + dgf * lg + dbf * lb
        val scale = 1f - lDestination
        val translate = 1 - scale * 1.0f
        val scaleMatrix = ColorMatrix(
            floatArrayOf(
                scale, 0f, 0f, 0f,
                dr * translate, 0f,
                scale, 0f, 0f,
                dg * translate, 0f, 0f,
                scale, 0f,
                db * translate, 0f, 0f, 0f, 1f, 0f
            )
        )
        scaleMatrix.preConcat(tintMatrix)
        return ColorMatrixColorFilter(scaleMatrix)
    }

}