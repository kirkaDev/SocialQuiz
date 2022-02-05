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
import com.desiredsoftware.socialquiz.ui.categories.CategoriesController
import com.desiredsoftware.socialquiz.ui.profile.ProfileController
import com.desiredsoftware.socialquiz.ui.splash.SplashController
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.bottomnavigation.BottomNavigationView
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity() {

    private var router: Router? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginLauncher = getSignInLauncher()

        val container: ViewGroup = findViewById(R.id.nav_host_fragment)
        router = Conductor.attachRouter(this, container, savedInstanceState)
        val bottomMenu: BottomNavigationView = findViewById(R.id.nav_view)

        bottomMenu.setOnItemSelectedListener{
            when (it.itemId){
                R.id.navigation_profile ->{
                    router?.pushController(RouterTransaction.with(ProfileController()))
                    true
                }
                R.id.navigation_home ->{
                    router?.pushController(RouterTransaction.with(CategoriesController()))
                    true
                }
                else ->{
                    true
                }
            }
        }

        router?.replaceTopController(RouterTransaction.with(SplashController()))
        //requestPermissions()
    }

    private fun getSignInLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { result: FirebaseAuthUIAuthenticationResult? ->
            if (result?.resultCode == -1)   // it means user is logged in
            {
                router?.replaceTopController(RouterTransaction.with(CategoriesController()))
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
            PERMISSIONS_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[2] == PackageManager.PERMISSION_GRANTED
                ) {
                    if (!router!!.hasRootController()) {
                        //mRouter!!.setRoot(RouterTransaction.with(AuthController(mLoginLauncher!!)))
                    }

                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return
            }

            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun requestPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), PERMISSIONS_REQUEST_CODE
        )
    }

    override fun onBackPressed() {
        if (!router!!.handleBack()) {
            super.onBackPressed()
        }
    }

    companion object{
        const val PERMISSIONS_REQUEST_CODE = 48573
        var loginLauncher: ActivityResultLauncher<Intent>? = null
    }
}