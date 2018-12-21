package com.example.anavia.clase16_gps

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat

import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), LocationListener, OnMapReadyCallback {

    var latitud = 0.0
    var longitud = 0.0
    var altitud = 0.0
    var lm:LocationManager? =null
    var mapa:GoogleMap?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //lo inicializo
        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //creamos el fragmento donde estará el mapa (fragmento especial para mapa supportMapFragment)
        val fragmentoMapa = supportFragmentManager.findFragmentById(R.id.frgMapa) as SupportMapFragment
        //al hacer la siguiente linea hay que implementar OnMapReadyCallback, debemos sobreescribir el método
        fragmentoMapa.getMapAsync(this)

        //solicito los permisos, hay que crearlos

        val permisos = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION)
        var granted=true

        for(permiso in permisos){
           granted = granted and (ActivityCompat.checkSelfPermission(this, permiso)== PackageManager.PERMISSION_GRANTED)
       }

        if(!granted){
            ActivityCompat.requestPermissions(this,permisos, 1)
        }else{
            //el gps va por dos opciones por tiempo y distancia
            //
            lm?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                1000,
                1f,
                this)
        }



    }

//Funcion para saber donde estoy, para que el mapa no salga 0,0,0 en medio del atlantico frente a Africa
    override fun onMapReady(p0: GoogleMap?) {
    mapa = p0

        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
           android.Manifest.permission.ACCESS_FINE_LOCATION)

        var granted = true
        for (cadaPermiso in permissions){
            granted = granted and (ActivityCompat.checkSelfPermission(this, cadaPermiso)== PackageManager.PERMISSION_GRANTED)
        }
        if (!granted){
            ActivityCompat.requestPermissions(this,permissions,1)
        }else{
            p0?.isMyLocationEnabled = true
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            1->{
                var granted=true
                for(permiso in permissions){
                    granted = granted and (ActivityCompat.checkSelfPermission(this, permiso)== PackageManager.PERMISSION_GRANTED)
                }

                if(grantResults.size>0 && granted){
                    mapa?.isMyLocationEnabled=true
                    lm?.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        1000,
                        1f,
                        this)
                }else{
                    Toast.makeText(this," Permiso GPS requerido",
                        Toast.LENGTH_LONG).show()
                }
            }
          /*  2->{
                var granted=true
                for(permiso in permissions){
                    granted = granted and (ActivityCompat.checkSelfPermission(this, permiso)== PackageManager.PERMISSION_GRANTED)
                }

                if(grantResults.size>0 && granted){
                    mapa?.isMyLocationEnabled=true

                    lm?.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        1000,
                        1f,
                        this)
                }else{
                    Toast.makeText(this,"FAIL Permiso GPS requerido",
                        Toast.LENGTH_LONG).show()
                }
            }*/
        }



        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

/*

clave del proyecto android
AIzaSyDRWf4NXJ3wgrXtnHUowK2Ad2wXS6JrAyY


para sacar la de android
Clase16_GPS->Task->Android->signingReport

SHA1: 0A:2B:40:D0:E8:BF:49:C5:29:F2:52:67:6D:8B:B7:E3:AB:B7:CD:AF



A class for handling geocoding and reverse geocoding.
Geocoding is the process of transforming a street address or other description
of a location into a (latitude, longitude) coordinate. Reverse geocoding is
the process of transforming a (latitude, longitude) coordinate into a (partial) address.
The amount of detail in a reverse geocoded location description may vary,
for example one might contain the full street address of the closest building,
while another might contain only a city name and postal code.
The Geocoder class requires a backend service that is not included in the core android framework.
The Geocoder query methods will return an empty list if there no backend service in the platform.
Use the isPresent() method to determine whether a Geocoder implementation exists.
 */
    override fun onLocationChanged(location: Location?) {



        latitud = location?.latitude.toString().toDouble()
        longitud = location?.longitude.toString().toDouble()
        altitud =location?.altitude.toString().toDouble()

    /*    var geocoder = Geocoder(this)
        var lugares = geocoder.getFromLocation(latitud,longitud,1)
    if (lugares.size>0){
        lblDireccion.text = lugares.get(0).getAddressLine(0)
    }else{
        lblDireccion.text="Sin dirección"
    }*/

    lblLatitud.text = latitud.toString()
        lblLongitud.text = longitud.toString()
        lblAltitud.text = altitud.toString()

    btnMarca.setOnClickListener {
        var marcador = LatLng(latitud,longitud)

        mapa?.addMarker(MarkerOptions().position(marcador))
    }



}


    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
     }

    override fun onProviderEnabled(provider: String?) {
     }

    override fun onProviderDisabled(provider: String?) {
    }
}
