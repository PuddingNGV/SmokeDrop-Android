package com.example.smokedrop.map


import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import org.osmdroid.bonuspack.location.POI
import org.osmdroid.util.GeoPoint

data class SmokePlace(val mId:Long, val mLocation: GeoPoint, val mTime: String) {

}