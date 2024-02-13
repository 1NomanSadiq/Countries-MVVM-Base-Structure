package com.gsc.app.utils.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Looper
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.gsc.app.utils.permissions.PermissionHandler
import com.gsc.app.utils.permissions.Permissions

object Locations {
    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private fun requestPermissions(
        context: Context,
        onPermissionsDenied: (() -> Unit)? = null,
        onGranted: () -> Unit = {}
    ) {
        checkLocationPermissions(context, object : PermissionHandler {
            override fun onGranted() {
                onGranted()
            }

            override fun onDenied() {
                onPermissionsDenied?.invoke()
            }
        })
    }

    private fun checkLocationPermissions(context: Context, handler: PermissionHandler) {
        Permissions.check(
            context,
            locationPermissions,
            handler
        )
    }

    fun Activity.requestLocationPermissions(
        onPermissionsDenied: (() -> Unit)? = null,
        onGranted: () -> Unit = {}
    ) = requestPermissions(this, onPermissionsDenied, onGranted)

    fun Fragment.requestLocationPermissions(
        onPermissionsDenied: (() -> Unit)? = null,
        onGranted: () -> Unit = {}
    ) = requireActivity().requestLocationPermissions(onPermissionsDenied, onGranted)

    @SuppressLint("MissingPermission")
    fun Activity.requestLocationUpdates(onResult: (LocationResult) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                onResult(p0)
            }
        }
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
        requestLocationPermissions {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    fun Fragment.requestLocationUpdates(onResult: (LocationResult) -> Unit) =
        requireActivity().requestLocationUpdates(onResult)
}