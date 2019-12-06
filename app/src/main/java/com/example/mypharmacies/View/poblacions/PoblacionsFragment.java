package com.example.mypharmacies.View.poblacions;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mypharmacies.Control.poblacionsDAO;
import com.example.mypharmacies.Model.Poblacio;
import com.example.mypharmacies.R;
import com.example.mypharmacies.View.farmacies.FarmaciesFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class PoblacionsFragment extends Fragment
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
        //https://stackoverflow.com/questions/19353255/
        //how-to-put-google-maps-v2-on-a-fragment-using-viewpager

    private GoogleMap mMap;
    static final LatLng CARCAIXENT  = new LatLng(39.119223, -0.453422);
    private ArrayList<Poblacio> poblacions;
    private Poblacio poble; private LatLng latLng;
    private poblacionsDAO myPoblacionsDAO;
    View root;
    // The general container that includes the fragment
    ViewGroup container;

    public PoblacionsFragment() {     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_poblacions, container, false);
        // To manage poblacions
        myPoblacionsDAO = new poblacionsDAO(root.getContext());
        poblacions = myPoblacionsDAO.getPoblacions();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.mapPoblacions);
        mapFragment.getMapAsync(this);
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        // Showing Poblacions
        for (int i=0; i < poblacions.size(); i++) {
            latLng = new LatLng(poblacions.get(i).getLat(), poblacions.get(i).getLon());
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(poblacions.get(i).getPoblacio())
            );
        }
        // Move the camera instantly to CARCAIXENT with a zoom of 10.
        // Center position of the map: the last poblacioLatLon
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CARCAIXENT, 10));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // The new target Fragment
        FarmaciesFragment farmaciesFragment = new FarmaciesFragment();
        //Getting the information to send to the Fragment
        Bundle bundle = new Bundle();
        bundle.putString("Poblacio", marker.getId());
        farmaciesFragment.setArguments(bundle);
        // Similar to Intents and startActivity but for Fragments
        FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        // Puting the targed Fragment into the container
        fragmentTransaction.replace(container.getId(),farmaciesFragment);
        fragmentTransaction.commit();
        return true;
    }
}