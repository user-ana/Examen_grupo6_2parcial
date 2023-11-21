package com.example.pm2examengrupo6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityMapa extends AppCompatActivity {

    private double latitud, longitud;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        latitud = intent.getDoubleExtra("latitud", 0);
        longitud = intent.getDoubleExtra("longitud", 0);
        nombre = intent.getStringExtra("nombre");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync((OnMapReadyCallback) this);


    }

    public void onMapReady(GoogleMap googleMap) {

        // Establece la ubicación del marcador
        LatLng location = new LatLng(latitud, longitud);

        // Agrega el marcador en la ubicación especificada
        googleMap.addMarker(new MarkerOptions().position(location).title(nombre));

        // Mueve la cámara del mapa a la ubicación del marcador
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
    }
}