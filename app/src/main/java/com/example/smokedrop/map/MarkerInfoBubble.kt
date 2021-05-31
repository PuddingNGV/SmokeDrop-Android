package com.example.smokedrop.map

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.smokedrop.R
import org.osmdroid.api.IMapView
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow

/**
 * [org.osmdroid.views.overlay.infowindow.MarkerInfoWindow] is the default
 * implementation of [org.osmdroid.views.overlay.infowindow.InfoWindow] for a
 * [org.osmdroid.views.overlay.Marker].
 *
 * It handles
 *
 * R.id.bubble_title          = [org.osmdroid.views.overlay.OverlayWithIW.getTitle],
 * R.id.bubble_subdescription = [org.osmdroid.views.overlay.OverlayWithIW.getSubDescription],
 * R.id.bubble_description    = [org.osmdroid.views.overlay.OverlayWithIW.getSnippet],
 * R.id.bubble_image          = [org.osmdroid.views.overlay.Marker.getImage]
 *
 * Description and sub-description interpret HTML tags (in the limits of the Html.fromHtml(String) API).
 * Clicking on the bubble will close it.
 *
 * <img alt="Class diagram around Marker class" width="686" height="413" src='./doc-files/marker-infowindow-classes.png'></img>
 *
 * @author M.Kergall
 */
class MarkerInfoBubble
    /**
     * @param layoutResId layout that must contain these ids: bubble_title,bubble_description,
     * bubble_subdescription, bubble_image
     * @param mapView
     */
    (layoutResId: Int, mapView: MapView?) : BasicInfoWindow(layoutResId, mapView) {
        /**
         * reference to the Marker on which it is opened. Null if none.
         * @return
         */
        var markerReference //reference to the Marker on which it is opened. Null if none.
                : Marker? = null
        protected set

                @SuppressLint("ResourceType")
                override fun onOpen(item: Any) {
            super.onOpen(item)
            markerReference = item as Marker
            if (mView == null) {
                Log.w(IMapView.LOGTAG, "Error trapped, MarkerInfoWindow.open, mView is null!")
                return
            }
/*
            val imageView = mView.findViewById<View>(R.drawable.custom_bubble) as ImageView
            val image = markerReference!!.image
            if (image != null) {
                imageView.setImageDrawable(image) //or setBackgroundDrawable(image)?
                imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                imageView.visibility = View.VISIBLE
            } else imageView.visibility = View.GONE
        */}

        override fun onClose() {
            super.onClose()
            markerReference = null
            //by default, do nothing else
        }
    }

