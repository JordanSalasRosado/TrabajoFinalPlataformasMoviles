package com.unimaq.rst

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.unimaq.rst.databinding.ActivityMainBinding
import com.unimaq.rst.ui.ajustes.AjustesFragment
import com.unimaq.rst.ui.reportes.CrearReporteFragment
import com.unimaq.rst.ui.reportes.ReportesFragment
import com.unimaq.rst.ui.ubicaciones.UbicacionesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_reportes, R.id.navigation_ubicaciones, R.id.navigation_ajustes
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setNavViewOnItemSelectedListener()
    }

    private fun setNavViewOnItemSelectedListener(){
        val bottomNavView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_reportes -> {
                    loadFragment(ReportesFragment())
                    true
                }

                R.id.navigation_ubicaciones -> {
                    loadFragment(UbicacionesFragment())
                    true
                }

                R.id.navigation_ajustes -> {
                    loadFragment(AjustesFragment())
                    true
                }
                else -> { false }
            }
        }
    }

    fun buttonVerClick(view: View){
        val fragment = CrearReporteFragment()
        val id = view.findViewById<View>(R.id.id).tag.toString()
        val data = Bundle()
        data.putLong("id", id.toLong())
        fragment.arguments = data
        loadFragment(fragment)

    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment)
        transaction.commit()
    }
}