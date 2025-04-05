package ee.ut.cs.homesecure.ui.map

import android.Manifest
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ee.ut.cs.homesecure.BuildConfig
import ee.ut.cs.homesecure.R
import ee.ut.cs.homesecure.live.LiveDataConnection
import ee.ut.cs.homesecure.ui.geofence.GeofenceBroadcastReceiver
import ee.ut.cs.homesecure.ui.geofence.createChannel

class MapFragment : Fragment() {
    private lateinit var liveDataConnection: LiveDataConnection
    private lateinit var map: GoogleMap
    private val geofenceList = ArrayList<Geofence>()
    private lateinit var geoClient: GeofencingClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        liveDataConnection = context?.let { LiveDataConnection(it) }!!
        liveDataConnection.observe(viewLifecycleOwner, { hasNetwork ->
            if (hasNetwork) {
                mapFragment?.getMapAsync(callback)
            } else {
                enableStrictModePolicy()
                alertNoInternet()
            }
        })
        context?.let { createChannel(it) }
        geoClient = LocationServices.getGeofencingClient(context)
        setGeofence()
    }

    private fun setGeofence() {
        val latitude = 59.3945433
        val longitude = 24.6454586
        val radius = 100f
        geofenceList.add(Geofence.Builder()
            .setRequestId("entry.key")
            .setCircularRegion(latitude, longitude, radius)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build())
    }

    private val geofenceIntent: PendingIntent by lazy {
        val intent = Intent(activity, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT)
    }


    private val callback = OnMapReadyCallback { googleMap ->
        //currently hardcorded but app feature need to add part to set home location for geofencing
        val coordCurrent = LatLng(59.395, 24.671)
        enableMyLocation(googleMap)
        setGeofenceCircleOptions(coordCurrent, googleMap)
        googleMap.addMarker(MarkerOptions().position(coordCurrent).title("My current location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordCurrent, 15F))
        setMapLongClick(googleMap)
    }

    private fun setGeofenceCircleOptions(coordCurrent: LatLng, googleMap: GoogleMap) {
        val circleOptions = CircleOptions()
            .center(coordCurrent)
            .radius(20.0)
            .fillColor(0x40ff0000)
            .strokeColor(Color.BLUE)
            .strokeWidth(2f)
        googleMap.addCircle(circleOptions)
    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            map.addMarker(MarkerOptions().position(latLng))
        }
    }

    private fun isPermissionGranted(): Boolean {
        return context?.let {
            ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION)
        } == PackageManager.PERMISSION_GRANTED
    }


    private fun enableMyLocation(map: GoogleMap) {
        if (isPermissionGranted()) {
            if (context?.let {
                    ActivityCompat.checkSelfPermission(it,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                }
                != PackageManager.PERMISSION_GRANTED) {
                return
            }
            map.isMyLocationEnabled = true
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(it,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION)
            }
        }
    }

    private fun triggerGeofence(): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofences(geofenceList)
        }.build()
    }

    private fun addGeofence() {
        if (context?.let {
                ActivityCompat.checkSelfPermission(it,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            }
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        geoClient.addGeofences(triggerGeofence(), geofenceIntent).run {
            addOnSuccessListener {
                Toast.makeText(context, "Geofence is set", Toast.LENGTH_SHORT).show()
            }
            addOnFailureListener {
                Toast.makeText(context, "Unable to add Geofence", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeGeofence() {
        geoClient.removeGeofences(geofenceIntent).run {
            addOnSuccessListener {
                //Toast.makeText(context, "Geofence deleted", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Geofence deleted")
            }
            addOnFailureListener {
                Log.d(TAG, "Unable to delete geofence")
            }
        }
    }

    private fun initializeGeofence() {
        if (authorizedLocation()) {
            validateGadgetAreaInitiateGeofence()
        } else {
            requestLocationPermission()

        }
    }

    private fun requestLocationPermission() {
        if (authorizedLocation())
            return
        // No location access granted.
        Log.d(TAG, "requesting location permission ")
        activity?.let {
            ActivityCompat.requestPermissions(
                it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE)
        }
    }


    private fun authorizedLocation(): Boolean {
        return (PackageManager.PERMISSION_GRANTED == context?.let {
            ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION)
        })
    }


    private fun validateGadgetAreaInitiateGeofence(resolve: Boolean = true) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(context)
        val locationResponses = client.checkLocationSettings(builder.build())

        locationResponses.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve) {
                try {
                    activity?.let {
                        exception.startResolutionForResult(it,
                            REQUEST_TURN_DEVICE_LOCATION_ON)
                    }
                } catch (sendEx: IntentSender.SendIntentException) {
                    Log.d(TAG, "Error retrieving location settings" + sendEx.message)
                }
            } else {
                Toast.makeText(context, "Enable Location settings", Toast.LENGTH_SHORT).show()
            }
        }
        locationResponses.addOnCompleteListener {
            if (it.isSuccessful) {
                addGeofence()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                enableMyLocation(map)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        validateGadgetAreaInitiateGeofence(false)
    }

    override fun onStart() {
        super.onStart()
        initializeGeofence()
    }

    override fun onDestroy() {
        super.onDestroy()
        removeGeofence()
    }

    //prevent app crash
    private fun enableStrictModePolicy() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }

    }

    private fun alertNoInternet() {
        val builder = context?.let {
            MaterialAlertDialogBuilder(it, R.style.ThemeOverlay_AppCompat_Dialog_Alert)
        }
        with(builder) {
            this!!.setTitle(resources.getString(R.string.no_internet))
            setMessage(resources.getString(R.string.wifi_settings))
            setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
                val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                startActivity(intent)
                dialog.dismiss()
            }
            setNegativeButton(resources.getString(R.string.no)) { dialog, which -> dialog.dismiss() }
            show()
        }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
        private const val REQUEST_TURN_DEVICE_LOCATION_ON = 20
        private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 3
        private const val TAG = "MapFragment"
    }

}
