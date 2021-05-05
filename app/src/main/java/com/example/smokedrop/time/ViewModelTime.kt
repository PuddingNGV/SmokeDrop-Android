package com.example.smokedrop.time

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ViewModelTime: ViewModel() {
    var timeNowLD = MutableLiveData<String>()
    var timeNextLD = MutableLiveData<String>()

    init  {
        nowSmoke()
    }

    private fun nowSmoke() {
        var timeNow = LocalDateTime.now()
        val formatTime = DateTimeFormatter.ofPattern("HH:mm:ss")
        timeNowLD.postValue(timeNow.format(formatTime))
        timeNextLD.postValue(nextSmoke(time = timeNow, hours = 2).format(formatTime))
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
