package com.example.smokedrop.time

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ViewModelTime: ViewModel() {

    var _clickState = MutableLiveData<Boolean>()
    val clickState: LiveData<Boolean>
        get() = _clickState

    var _timeNowLD = MutableLiveData<String>()
    val timeNowLD: LiveData<String>
        get() = _timeNowLD

    var _timeNextLD = MutableLiveData<String>()
    val timeNextLD:LiveData<String>
            get()=_timeNextLD

    var _smokeCountLD = MutableLiveData<String>()
    val smokeCountLD:LiveData<String>
    get() = _smokeCountLD

    init  {
        takeHistory()
    }

    private fun takeHistory() {
        //Connection Room Sim take old data
        val timeNow = LocalDateTime.now()
        val formatTime = DateTimeFormatter.ofPattern("HH:mm:ss")
        //timeNowLD.postValue(timeNow.format(formatTime))
        //timeNextLD.postValue(nextSmoke(time = timeNow, hours = 1).format(formatTime))
    }


  fun onClickButton() {
      val timeNow = LocalDateTime.now()
      val formatTime = DateTimeFormatter.ofPattern("HH:mm:ss")
      _timeNowLD.value = timeNow.format(formatTime).toString()
    }























    private fun nextSmoke(time: LocalDateTime, day: Long = 0, hours: Long = 0, minutes:Long = 0):LocalDateTime {
        return time.plusDays(day).plusHours(hours).plusMinutes(minutes)
    }

    fun timeMinus(day: Long = 0, hours: Long = 0, minutes:Long = 0){
        val timeNow = LocalDateTime.now()
        timeNow.minusDays(day)
        timeNow.minusHours(hours)
        timeNow.minusMinutes(minutes)
    }
}

class MyHandler {
    fun onEnabled(enabled: Boolean) {
        println("РРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРРР")
    }
}
