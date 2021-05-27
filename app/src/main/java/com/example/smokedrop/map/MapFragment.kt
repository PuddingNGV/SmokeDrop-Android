package com.example.smokedrop.map

import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.example.smokedrop.R
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.FolderOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment() {
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var map: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onStart() {
        super.onStart()

        // Find and start map parameters
        getInstance().load(activity, PreferenceManager.getDefaultSharedPreferences(activity))
        map = requireView().findViewById(R.id.mapview)
        map.setTileSource(TileSourceFactory.HIKEBIKEMAP)
        map.setMultiTouchControls(true)
        val mapController = map.controller
        //map.overlayManager.tilesOverlay.setColorFilter(TilesOverlay.INVERT_COLORS)
        mapController.setZoom(16)
        val startPoint = GeoPoint(55.707772, 52.356435)
        mapController.setCenter(startPoint)




        map.overlayManager.tilesOverlay.setColorFilter(filterMaps())

/*

        // onClick callback
        markerMaps().setOnClickListener {points, point ->
            Toast.makeText(
                map.context,
                "You clicked " + (points[point] as LabelledGeoPoint).label,
                Toast.LENGTH_SHORT
            ).show()
        }



         */
        markerMaps()

        map.onResume()

    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }





    private fun markerMaps() {

        val startPoint = GeoPoint(55.707772, 52.356435)
        val poiMarker = Marker(map)

        val points = ArrayList<GeoPoint>()
        for (i in 0..1000) {
            points.add(
                LabelledGeoPoint(
                    55.70 + Math.random() * 5, 52.356435 + Math.random() * 5
                )
            )
        }
        val poiMarkers = RadiusMarkerClusterer(activity)
        for (poi in points) {
            val poiMarker = Marker(map)
            poiMarker.title = "SUKA"
            poiMarker.position = poi
            poiMarker.icon = activity?.let { ContextCompat.getDrawable(it, R.drawable.location_pin) }
            poiMarkers.add(poiMarker)
        }
        poiMarkers.setIcon(activity?.let { AppCompatResources.getDrawable(it, R.drawable.data_usage)?.toBitmap(100,100) })
        poiMarkers.setRadius(500)

        map.overlays.add(poiMarkers)

        //poiMarkers.setIcon(activity?.let { AppCompatResources.getDrawable(it, R.drawable.marker_cluster)?.toBitmap() })

        //startMarker.icon = activity?.let { ContextCompat.getDrawable(it, R.drawable.location_pin) }


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