package com.example.smokedrop

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import com.example.smokedrop.info.InfoFragment
import com.example.smokedrop.map.MapFragment
import com.example.smokedrop.settings.SettingsFragment
import com.example.smokedrop.time.TimeFragment
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import java.util.*


class SmokeDrop : AppCompatActivity() {
    val RequestPermissionCode = 1
    var mLocation: Location? = null
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnNavigationItemSelectedListener(navigationItemSelectedListener())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (savedInstanceState == null) {
            val fragment = TimeFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, fragment.javaClass.getSimpleName())
                .commit()
        }
    }

fun lan (view: View) {
    getLastLocation()
}

    @SuppressLint("MissingPermission")
    fun getLastKnownLocation(context: Context) {
/*        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers: List<String> = locationManager.getProviders(true)
        var location: Location? = null
        for (i in providers.size - 1 downTo 0) {
            location = locationManager.getLastKnownLocation(providers[i])
            println("LOX " + location)
            if (location != null) {
                break
            }
        }
        val gps = DoubleArray(2)
        if (location != null) {
            gps[0] = location.getLatitude()
            gps[1] = location.getLongitude()
            Log.e("gpsLat", gps[0].toString())
            Log.e("gpsLong", gps[1].toString())

        }
         */
    }

    private fun getLastLocation(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission()
        }
        else{
            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener {location: Location? ->
                    mLocation = location
                    if(location != null){
                        println(location.latitude.toString() + " " + location.longitude.toString())
                        //println(location.longitude.toString())
                    }
                    else{
                        println("LOCATION_DENIED")
                        println("LOCATION_DENIED")
                    }
                }
        }
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), RequestPermissionCode)
        this.recreate()
    }


    private fun navigationItemSelectedListener() = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.timeFragment -> {
                val fragment = TimeFragment()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.infoFragment -> {
                val fragment = InfoFragment()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.mapFragment -> {
                val fragment = MapFragment()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.settingsFragment -> {
                val fragment = SettingsFragment()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
