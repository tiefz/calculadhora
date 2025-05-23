package br.com.insertkoin.calculadhora

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import br.com.insertkoin.calculadhora.presentation.location.LocationManager
import br.com.insertkoin.calculadhora.presentation.theme.CalculadhoraTheme
import br.com.insertkoin.calculadhora.presentation.ui.HomeScreen
import br.com.insertkoin.calculadhora.presentation.viewmodel.HomeViewModel
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModel()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        checkAndRequestLocationPermission()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            setupGeofenceBroadcastReceiver()
        } else {
            checkAndRequestLocationPermission()
        }
        setContent {
            CalculadhoraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(viewModel, Modifier.padding(innerPadding))
                }
            }
        }
    }

    private fun checkAndRequestLocationPermission() {
        val fineLocationGranted = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val backgroundLocationGranted = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        when {
            !fineLocationGranted -> {
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
            }

            !backgroundLocationGranted -> {
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION))
            }

            else -> {
                setupGeofenceBroadcastReceiver()
            }
        }
    }

    private fun setupGeofenceBroadcastReceiver() {
        val geofencingClient = LocationServices.getGeofencingClient(this)
        val locationManager = LocationManager(this, geofencingClient)
        val latitude = -23.553854
        val longitude = -46.657945
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.setupGeofence(latitude, longitude)
        }
    }
}