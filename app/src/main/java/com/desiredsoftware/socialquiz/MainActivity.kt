package com.desiredsoftware.socialquiz

import android.content.Intent
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

    override fun onBackPressed() {
        if (!router!!.handleBack()) {
            super.onBackPressed()
        }
    }

    companion object{
        var loginLauncher: ActivityResultLauncher<Intent>? = null
    }
}