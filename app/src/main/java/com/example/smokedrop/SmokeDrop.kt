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
    private val RequestPermissionCode = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnNavigationItemSelectedListener(
            navigationItemSelectedListener()
        )

        if (savedInstanceState == null) {
            val fragment = TimeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment.javaClass.getSimpleName())
                .commit()
        }
    }

    fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission()
        }
    }

        private fun requestPermission() {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                RequestPermissionCode
            )
            this.recreate()
        }


        private fun navigationItemSelectedListener() =
            BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.timeFragment -> {
                        val fragment = TimeFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragment_container,
                                fragment,
                                fragment.javaClass.getSimpleName()
                            )
                            .commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.infoFragment -> {
                        val fragment = InfoFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragment_container,
                                fragment,
                                fragment.javaClass.getSimpleName()
                            )
                            .commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.mapFragment -> {
                        val fragment = MapFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragment_container,
                                fragment,
                                fragment.javaClass.getSimpleName()
                            )
                            .commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.settingsFragment -> {
                        val fragment = SettingsFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragment_container,
                                fragment,
                                fragment.javaClass.getSimpleName()
                            )
                            .commit()
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            }
    }

