package com.example.janhon.running.Running;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.janhon.running.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class RunningFragment extends Fragment implements
        OnMapReadyCallback {
    private GoogleMap map;
    private Marker marker_nowLocation ;
    private LatLng nowLocation = new LatLng(22.111, 121.555);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_running);
//       SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, //container傳入
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_running, container, false); //inflater 載入器,載入layout.fragment_score
        SupportMapFragment mapFragment  = null;
        if (getFragmentManager() != null) {
            mapFragment = (SupportMapFragment)getChildFragmentManager()
                    .findFragmentById(R.id.map);
        }
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        setUpMap();

    }

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }
        map.getUiSettings().setZoomControlsEnabled(true);
        moveMap();
        addListeners();
        addMarkers();
        //map.setInfoWindowAdapter(new MyInfoWindowAdapter());
        draw2D();
    }

    private void addMarkers() {
        marker_nowLocation = map.addMarker(new MarkerOptions().position(nowLocation)
//                .title(getString(R.string.marker_title_taroko))
//                .snippet(getString(R.string.marker_snippet_taroko))
                .draggable(true));

    }

    private void moveMap() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(22.111, 121.555))
                .zoom(7)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory
                .newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);
    }

    private void addListeners() {
        MyMarkerListener myMarkerListener = new MyMarkerListener();
        map.setOnMarkerClickListener(myMarkerListener);
        map.setOnInfoWindowClickListener(myMarkerListener);
    }

    private class MyMarkerListener implements GoogleMap.OnMarkerClickListener,
            GoogleMap.OnInfoWindowClickListener {
        @Override
        public boolean onMarkerClick(Marker marker) {
            showToast(marker.getTitle());
            return false;
        }

        @Override
        public void onInfoWindowClick(Marker marker) {
            showToast(marker.getTitle());
        }

        private void showToast(String message) {
            //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void draw2D() {

        map.addCircle(
                new CircleOptions()
                        .center(nowLocation)
                        .radius(100000)
                        .strokeWidth(5)
                        .strokeColor(Color.TRANSPARENT)
                        .fillColor(Color.argb(100, 0, 0, 100)));
    }


}
