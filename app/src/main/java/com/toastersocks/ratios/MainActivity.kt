package com.toastersocks.ratios

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {

    private var coordinator = Coordinator(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("d", "MainActivity.onCreate is running")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("d", "About to run Coordinator.start()")
        coordinator.start()
    }
}
