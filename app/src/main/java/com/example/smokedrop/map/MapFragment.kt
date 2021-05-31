package com.example.smokedrop.map

import android.content.Context
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
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
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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

        //Min Max zoom set
        mapController.setZoom(15.0)

        // GeoPoint Start LiveData convert
        val startPoint = GeoPoint(55.707772, 52.356435)
        mapController.setCenter(startPoint)

        //setFilter mb livedata?
        map.overlayManager.tilesOverlay.setColorFilter(filterMaps())

        markerMaps()
        map.onResume()

    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }


    private fun markerMaps() {

        val pois = ArrayList<SmokePlace>()
        val formatTime = DateTimeFormatter.ofPattern("yyyy.MM.dd" +"\n"+ "HH:mm:ss")
        println("__________________________________________")
        for (i in 0..1000) {
            pois.add(SmokePlace(i.toLong(),
                LabelledGeoPoint(55.70 + Math.random() * 0.1,
                    52.356435 + Math.random() * 0.1),
                LocalDateTime.now().format(formatTime)
                )
            )
        }
        println("__________________________________________")
        println(pois.size)
        println(pois[1])


         /*

        val points = ArrayList<GeoPoint>()
        for (i in 0..1000) {
            points.add(
                LabelledGeoPoint(
                    55.70 + Math.random() * 0.1, 52.356435 + Math.random() * 0.1
                )
            )
        }
        */

        val poiMarkers = RadiusMarkerClusterer(activity)
        for (poi in pois) {
            val poiMarker = Marker(map)
            //
            poiMarker.position = poi.mLocation
            poiMarker.title = poi.mTime
            poiMarker.icon = activity?.let { ContextCompat.getDrawable(it, R.drawable.location_pin) }
            poiMarkers.add(poiMarker)
            //poiMarker.setImage(activity?.let { ContextCompat.getDrawable(it, R.drawable.custom_bubble) })
            poiMarker.infoWindow = MarkerInfoBubble(R.layout.bubble_maket, map)

        }
        poiMarkers.setIcon(activity?.let { AppCompatResources.getDrawable(it, R.drawable.data_usage)?.toBitmap(100,100) })
        poiMarkers.setRadius(300)

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