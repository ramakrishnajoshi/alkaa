package com.escodro.alkaa.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.escodro.alkaa.R
import timber.log.Timber

/**
 * Main application [AppCompatActivity].
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")

        setContentView(R.layout.activity_main)
    }
}
