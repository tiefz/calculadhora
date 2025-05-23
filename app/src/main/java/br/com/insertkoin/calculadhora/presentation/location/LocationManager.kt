package br.com.insertkoin.calculadhora.presentation.location

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.RequiresPermission
import br.com.insertkoin.calculadhora.presentation.broadcast.GeofenceBroadcastReceiver
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest

class LocationManager(
    private val context: Context,
    private val geofencingClient: GeofencingClient
) {
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun setupGeofence(lat: Double, lng: Double) {
        val geofence = Geofence.Builder()
            .setRequestId("local_id")
            .setCircularRegion(lat, lng, 150f)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        val geofencePendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, GeofenceBroadcastReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent)
            .addOnSuccessListener {
                Log.i("LocationManager", "Geofence registrado com sucesso")
            }
            .addOnFailureListener { e ->
                Log.e("LocationManager", "Erro ao adicionar geofence", e)
            }
    }
}