package com.google.uddd_project

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

import com.google.uddd_project.databinding.ActivityMapsBinding
import com.vmadalin.easypermissions.EasyPermissions

class apsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    companion object {
        // SharedPreferences
        private const val KEY_SHARED_PREFERENCE = "com.rwRunTrackingApp.sharedPreferences"
        private const val KEY_IS_TRACKING = "com.rwRunTrackingApp.isTracking"
        // Permission
        private const val REQUEST_CODE_FINE_LOCATION = 1
        private const val REQUEST_CODE_ACTIVITY_RECOGNITION = 2
    }

    private var isTracking: Boolean
        get() = this.getSharedPreferences(KEY_SHARED_PREFERENCE, Context.MODE_PRIVATE).getBoolean(KEY_IS_TRACKING, false)
        set(value) = this.getSharedPreferences(KEY_SHARED_PREFERENCE, Context.MODE_PRIVATE).edit().putBoolean(KEY_IS_TRACKING, value).apply()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Switch to AppTheme for displaying the activity

        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        // Set up button click events
        binding.startButton.setOnClickListener {
            // Clear the PolylineOptions from Google Map
            mMap.clear()

            // Update Start & End Button
            isTracking = true
            updateButtonStatus()

            // Reset the display text
            updateAllDisplayText(0, 0f)

            startTracking()
        }
        binding.endButton.setOnClickListener { endButtonClicked() }

        // Update layouts
        updateButtonStatus()

        if (isTracking) {
            startTracking()
        }
    }

    // UI related codes
    private fun updateButtonStatus() {
        binding.startButton.isEnabled = !isTracking
        binding.endButton.isEnabled = isTracking
    }

    private fun updateAllDisplayText(stepCount: Int, totalDistanceTravelled: Float) {
        binding.numberOfStepTextView.text =  String.format("Step count: %d", stepCount)
        binding.totalDistanceTextView.text = String.format("Total distance: %.2fm", totalDistanceTravelled)

        val averagePace = if (stepCount != 0) totalDistanceTravelled / stepCount.toDouble() else 0.0
        binding.averagePaceTextView.text = String.format("Average pace: %.2fm/ step", averagePace)
    }

    private fun endButtonClicked() {
        AlertDialog.Builder(this)
            .setTitle("Are you sure to stop tracking?")
            .setPositiveButton("Confirm") { _, _ ->
                isTracking = false
                updateButtonStatus()
                stopTracking()
            }.setNegativeButton("Cancel") { _, _ ->
            }
            .create()
            .show()
    }

    // Tracking
    @SuppressLint("CheckResult")
    private fun startTracking() {

    }

    private fun stopTracking() {

    }

    // Map related codes
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Hong Kong and move the camera
        val latitude = 10.8860
        val longitude = 106.7821
        val location = LatLng(latitude, longitude)

        val zoomLevel = 17f
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))
    }
}