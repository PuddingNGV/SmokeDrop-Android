package com.example.smokedrop.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ViewModelMap: ViewModel() {

    private var _geoPointNow = MutableLiveData<GeoPoint>()
    val geoPointNow: LiveData<GeoPoint>
        get() = _geoPointNow

    private var _pois = MutableLiveData<ArrayList<SmokePlace>>()
    val pois: LiveData<ArrayList<SmokePlace>>
        get() = _pois


    init  {
        takeHistory()
        _geoPointNow.value = GeoPoint(55.707772, 52.356435)
    }

    private fun takeHistory() {
        //Connection Room Sim take old data
        val points = ArrayList<SmokePlace>()
        for (i in 0..1000) {
            points.add(SmokePlace(i.toLong(),
                LabelledGeoPoint(55.70 + Math.random() * 0.1,
                    52.356435 + Math.random() * 0.1),
                formatConversionTime(LocalDateTime.now())
                )
            )
        }
        _pois.value = points
    }




    private fun timePlus(time: LocalDateTime, day: Long = 0, hours: Long = 0, minutes:Long = 0):String {
        return formatConversionTime(time.plusDays(day).plusHours(hours).plusMinutes(minutes))
    }

    private fun timeMinus(time: LocalDateTime, day: Long = 0, hours: Long = 0, minutes:Long = 0):String {
        return formatConversionTime(time.minusDays(day).minusHours(hours).minusMinutes(minutes))
    }
}

private fun formatConversionTime(time: LocalDateTime):String {
    val formatTime = DateTimeFormatter.ofPattern("yyyy.MM.dd" +"\n"+ "HH:mm:ss")
    return time.format(formatTime)
}


