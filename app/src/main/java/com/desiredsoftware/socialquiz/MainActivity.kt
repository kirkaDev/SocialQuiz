package com.desiredsoftware.socialquiz

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.desiredsoftware.socialquiz.ui.auth.AuthController
import com.desiredsoftware.socialquiz.ui.categories.CategoriesController
import com.desiredsoftware.socialquiz.ui.profile.ProfileController
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.bottomnavigation.BottomNavigationView
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity() {

    private var mRouter: Router? = null

    var mLoginLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLoginLauncher = getSignInLauncher()

        val container: ViewGroup = findViewById(R.id.nav_host_fragment)
        mRouter = Conductor.attachRouter(this, container, savedInstanceState)

        val bottomMenu: BottomNavigationView = findViewById(R.id.nav_view)

        bottomMenu.setOnItemSelectedListener{
            when (it.itemId){
                R.id.navigation_profile ->{
                    mRouter?.pushController(RouterTransaction.with(ProfileController()))
                    true
                }
                else ->{
                    true
                }
            }
        }

        requestPermissions()
    }

    private fun getSignInLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { result: FirebaseAuthUIAuthenticationResult? ->
            if (result?.resultCode == -1)   // it means user is logged in
            {
                mRouter?.replaceTopController(RouterTransaction.with(CategoriesController()))
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.auth_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[2] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[3] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[4] == PackageManager.PERMISSION_GRANTED
                ) {
                    if (!mRouter!!.hasRootController()) {
                        mRouter!!.setRoot(RouterTransaction.with(AuthController(mLoginLauncher!!)))
                    }

                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun requestPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), 1
        )
    }

    override fun onBackPressed() {
        if (!mRouter!!.handleBack()) {
            super.onBackPressed()
        }
    }
}