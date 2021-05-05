package com.example.smokedrop.time

import android.os.Handler
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ModelTime: Observable() {

    var time: String = ""
        set(value) {
            val timeNow = LocalDateTime.now()
            val formatTime = DateTimeFormatter.ofPattern("HH:mm:ss")
            timeNow.format(formatTime)
            field = value
        }

/*    fun timePlus(day: Long = 0, hours: Long = 0, minutes:Long = 0){
        val timeNow = LocalDateTime.now()
        timeNow.plusDays(day)
        timeNow.plusHours(hours)
        timeNow.plusMinutes(minutes)
    }
    fun timeMinus(day: Long = 0, hours: Long = 0, minutes:Long = 0){
        val timeNow = LocalDateTime.now()
        timeNow.minusDays(day)
        timeNow.minusHours(hours)
        timeNow.minusMinutes(minutes)
    }
    */
}