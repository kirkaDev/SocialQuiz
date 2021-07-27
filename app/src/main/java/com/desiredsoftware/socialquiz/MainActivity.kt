package com.desiredsoftware.socialquiz

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.ui.AppBarConfiguration
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.desiredsoftware.socialquiz.ui.categories.CategoriesController
import com.google.android.material.bottomnavigation.BottomNavigationView
import moxy.MvpAppCompatActivity


class MainActivity : MvpAppCompatActivity() {

    private val requestMultiplePermissions =     registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.entries.forEach {
            Log.e("DEBUG", "${it.key} = ${it.value}")
        }
    }

    private var router: Router? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        var container: ViewGroup = findViewById(R.id.nav_host_fragment)

        router = Conductor.attachRouter(this, container, savedInstanceState)

        if (!router!!.hasRootController()) {
            router!!.setRoot(RouterTransaction.with(CategoriesController()))
        }

        Log.d("Main activity", "onCreate is working")

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_profile
            )
        )

        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
        )
    }

    override fun onBackPressed() {
        if (!router!!.handleBack()) {
            super.onBackPressed()
        }
    }
}