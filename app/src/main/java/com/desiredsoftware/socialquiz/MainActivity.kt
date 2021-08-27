package com.desiredsoftware.socialquiz

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.desiredsoftware.socialquiz.ui.auth.AuthController
import com.desiredsoftware.socialquiz.ui.categories.CategoriesController
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity() {

    private var mRouter: Router? = null

    var loginLauncher: ActivityResultLauncher<Intent>? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginLauncher = getSignInLauncher()

        val container: ViewGroup = findViewById(R.id.nav_host_fragment)
        Log.d("Main activity", "onCreate is working")

        mRouter = Conductor.attachRouter(this, container, savedInstanceState)

        requestPermissions()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getSignInLauncher(): ActivityResultLauncher<Intent> {
        val signInLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { result: FirebaseAuthUIAuthenticationResult? ->
            if (result?.resultCode == -1)   // it means user is logged in
            {
                mRouter?.pushController(RouterTransaction.with(CategoriesController()))
            } else {
                Toast.makeText(
                    this,
                    "Вы не авторизованы, попробуйте повторить вход",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        return signInLauncher
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    if (!mRouter!!.hasRootController()) {
                        mRouter!!.setRoot(RouterTransaction.with(AuthController(loginLauncher!!)))
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ), 1
        )
    }

    override fun onBackPressed() {
        if (!mRouter!!.handleBack()) {
            super.onBackPressed()
        }
    }
}