package com.example.smokedrop.time

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ViewModelTime(application: Application) : AndroidViewModel(application) {

    private var _clickState = MutableLiveData<Boolean>()
    val clickState: LiveData<Boolean>
        get() = _clickState

    private var _smokeCountTotal = MutableLiveData<Int>()
    val smokeCountTotal: LiveData<Int>
        get() = _smokeCountTotal

    private var _smokeCountToDay = MutableLiveData<Int>()
    val smokeCountToDay:LiveData<Int>
        get() = _smokeCountToDay

    private var _timeLast = MutableLiveData<String>()
    val timeLast: LiveData<String>
        get() = _timeLast

    private var _timeNext = MutableLiveData<String>()
    val timeNext:LiveData<String>
        get()=_timeNext

    private var _smokeCountRemain = MutableLiveData<Int>()
    val smokeCountRemain:LiveData<Int>
        get() = _smokeCountRemain

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var _location = MutableLiveData<Location>()
    val location:LiveData<Location>
        get() = _location





/*
    var _clickState = MutableLiveData<Boolean>()
    val clickState: LiveData<Boolean>
        get() = _clickState

    var _smokeCountTotal = MutableLiveData<Int>()
    val smokeCountTotal: LiveData<Int>
        get() = _smokeCountTotal


    var _smokeCountToDay = MutableLiveData<Int>()
    val smokeCountToDay:LiveData<Int>
        get() = _smokeCountToDay

    var _timeLast = MutableLiveData<String>()
    val timeLast: LiveData<String>
        get() = _timeLast

    var _timeNext = MutableLiveData<String>()
    val timeNext:LiveData<String>
        get()=_timeNext

    var _smokeCountRemain = MutableLiveData<Int>()
    val smokeCountRemain:LiveData<Int>
        get() = _smokeCountRemain
*/
    init  {
        takeHistory(application)
    }

    private fun takeHistory(application: Application) {
        //Connection Room Sim take old data
        val time = LocalDateTime.now()
        _smokeCountTotal.value = 2050
        _smokeCountToDay.value = 3
        _timeLast.value = timeMinus(time, hours = 3, minutes = 17)
        _timeNext.value = timePlus(time, hours = 1, minutes = 20)
        _smokeCountRemain.value = 10
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application.applicationContext)
    }


  fun onClickButton() {
      val timeNow = LocalDateTime.now()
      formatConversionTime(timeNow)
      _timeLast.value = formatConversionTime(timeNow)
      _timeNext.value = timePlus(timeNow, hours = 1, minutes = 12)

      _smokeCountTotal.value = _smokeCountTotal.value?.plus(1)
      _smokeCountToDay.value = _smokeCountToDay.value?.plus(1)
      _smokeCountRemain.value = _smokeCountRemain.value?.minus(1)
      getLocationNow()
    }

    @SuppressLint("MissingPermission")
    private fun getLocationNow() {
        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener {location: Location? ->
            if(location != null){
                println(location.latitude.toString() + " " + location.longitude.toString())
                _location.value = location
                }
            else{
                println("LOCATION_DENIED")
                println("LOCATION_DENIED")
            }
        }
    }

    private fun timePlus(time: LocalDateTime, day: Long = 0, hours: Long = 0, minutes:Long = 0):String {
        return formatConversionTime(time.plusDays(day).plusHours(hours).plusMinutes(minutes))
    }

    private fun timeMinus(time: LocalDateTime, day: Long = 0, hours: Long = 0, minutes:Long = 0):String {
        return formatConversionTime(time.minusDays(day).minusHours(hours).minusMinutes(minutes))
    }
}

    private fun formatConversionTime(time: LocalDateTime):String {
        val formatTime = DateTimeFormatter.ofPattern("HH:mm:ss")
        return time.format(formatTime)
    }

class MyHandler {
    fun onEnabled(enabled: Boolean) {
        println("РРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРР")
    }
}
