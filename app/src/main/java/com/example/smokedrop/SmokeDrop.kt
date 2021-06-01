package com.example.smokedrop

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import com.example.smokedrop.info.InfoFragment
import com.example.smokedrop.map.MapFragment
import com.example.smokedrop.settings.SettingsFragment
import com.example.smokedrop.time.TimeFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class SmokeDrop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_app)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnNavigationItemSelectedListener(navigationItemSelectedListener())

        if (savedInstanceState == null) {
            val fragment = TimeFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, fragment.javaClass.getSimpleName())
                .commit()
        }

        val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                // Got last known location. In some rare situations this can be null.
            }

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
