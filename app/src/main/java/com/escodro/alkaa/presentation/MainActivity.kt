package com.escodro.alkaa.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }
}
