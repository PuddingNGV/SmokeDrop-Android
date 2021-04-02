package com.example.smokedrop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smokedrop.info.InfoFragment
import com.example.smokedrop.map.MapFragment
import com.example.smokedrop.time.TimeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class SmokeDrop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnNavigationItemSelectedListener(navigationItemSelectedListener())
        setSupportActionBar(findViewById(R.id.toolbar))

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
        }
        false
    }
}
