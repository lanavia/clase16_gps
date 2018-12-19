package com.example.anavia.clase16_gps

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), LocationListener {



    var lm:LocationManager? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //lo inicializo
        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager

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
            lm?.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000,
                1f,
                this)
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
                    lm?.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        100,
                        1f,
                        this)
                }else{
                    Toast.makeText(this," Permiso GPS requerido",
                        Toast.LENGTH_LONG).show()
                }
            }
        }



        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

/*

clave del proyecto android
AIzaSyDRWf4NXJ3wgrXtnHUowK2Ad2wXS6JrAyY


para sacar la de android
Clase16_GPS->Task->Android->signingReport

SHA1: 0A:2B:40:D0:E8:BF:49:C5:29:F2:52:67:6D:8B:B7:E3:AB:B7:CD:AF

 */
    override fun onLocationChanged(location: Location?) {
        lblLatitud.text = location?.latitude.toString()
        lblLongitud.text = location?.longitude.toString()
        lblAltitud.text = location?.altitude.toString()
    }


    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
     }

    override fun onProviderEnabled(provider: String?) {
     }

    override fun onProviderDisabled(provider: String?) {
    }
}
