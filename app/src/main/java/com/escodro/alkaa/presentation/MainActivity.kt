package com.escodro.alkaa.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.escodro.alkaa.R
import com.escodro.alkaa.databinding.ActivityMainBinding
import timber.log.Timber

/**
 * Main application [AppCompatActivity].
 */
internal class MainActivity : AppCompatActivity() {

    @Suppress("LateinitUsage")
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        val host = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment?

        if (host == null) {
            Timber.e("NavHostFragment is null ")
            return
        }

        NavigationUI.setupWithNavController(binding.navBottomView, host.navController)
    }
}
