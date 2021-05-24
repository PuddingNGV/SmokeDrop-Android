package com.example.smokedrop

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.smokedrop.info.InfoFragment
import com.example.smokedrop.map.MapFragment
import com.example.smokedrop.settings.SettingsFragment
import com.example.smokedrop.time.TimeFragment
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
