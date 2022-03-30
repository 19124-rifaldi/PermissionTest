package com.binar.testpermission

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private lateinit var loadImage : Button
    private lateinit var image : ImageView
    private lateinit var buttonLocation: Button
    private lateinit var buttonSnack : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonLocation = findViewById(R.id.button2)
        loadImage = findViewById<Button>(R.id.button)
        buttonSnack = findViewById(R.id.button_snack)

        loadImage.setOnClickListener{
            loadImage()
        }

        buttonLocation.setOnClickListener{
            checkPermission()
        }

        buttonSnack.setOnClickListener{
            snack()
        }

    }

    private fun snack(){
        val snackbar = Snackbar.make(findViewById(R.id.activity_main),"ini snackbar long",Snackbar.LENGTH_LONG)
        snackbar.setAction("Dismiss") {
            snackbar.dismiss()
        }
        snackbar.show()
    }

    fun loadImage (){

        image = findViewById(R.id.imageView)
        Glide.with(this)
            .load("https://img.icons8.com/plasticine/2x/flower.png")
            .circleCrop()
            .into(image)
    }

    @SuppressLint("MissingPermission")
    fun getLongLat() {
        val locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val location: Location? =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        val latLongText = "Lat: ${location?.latitude} Long : ${location?.longitude}"
        Log.d(MainActivity::class.simpleName, latLongText)
        Toast.makeText(
            this,
            latLongText,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun requestLocationPermission() {
        requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 201)
    }

    private fun checkPermission(){
        val permissionCheck = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Location DIIZINKAN", Toast.LENGTH_LONG).show()
            getLongLat()
        } else {
            Toast.makeText(this, "Permission Location DITOLAK", Toast.LENGTH_LONG).show()
            requestLocationPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            201 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION
                ) {
                    Toast.makeText(this, "Permissions for Location Permitted", Toast.LENGTH_LONG)
                        .show()
                    getLongLat()
                } else {
                    Toast.makeText(this, "Permissions for Location Denied", Toast.LENGTH_LONG)
                        .show()
                }
            }
            else -> {
                Toast.makeText(this, "The request code doesn't match", Toast.LENGTH_LONG).show()
            }
        }
    }
}




