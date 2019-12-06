package com.example.mypharmacies.View.farmacies;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mypharmacies.Control.farmaciesDAO;
import com.example.mypharmacies.Model.Farmacia;
import com.example.mypharmacies.R;
import com.example.mypharmacies.View.infoFarmacia.InfoFarmaciaFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class FarmaciesFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ArrayList<Farmacia> farmacies;
    private LatLng latLng;
    private farmaciesDAO myFarmaciesDAO;
    String codiPoblacio;
    // codiFarmacies: array marker 0 -> codi primera farmacia del poble
    // codiFarmacies: array marker 1 -> codi segona farmacia del poble ...
    ArrayList <String> codiFarmacies;
    // The global container
    ViewGroup container;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.container = container;
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_farmacies, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.mapFarmacies);
        mapFragment.getMapAsync(this);
        // To manage Farmàcies
        codiPoblacio=getArguments().getString("Poblacio");
        myFarmaciesDAO = new farmaciesDAO(root.getContext());
        farmacies = myFarmaciesDAO.getFarmaciesPoblacio(codiPoblacio);
        codiFarmacies = new ArrayList<>();
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        // Showing Farmacies
        for (int i=0; i < farmacies.size(); i++) {
            latLng = new LatLng(farmacies.get(i).getLat(), farmacies.get(i).getLon());
            // String nomPoblacio = myFarmaciesDAO.getNomPoblacio(farmacies.get(i).getCodi());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(farmacies.get(i).getNom())
                    .snippet(farmacies.get(i).getAdresa())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            );
            Log.d("marker", "Array: " + i + " -- " + marker.getId() +" Codi farmàcia: " +farmacies.get(i).getCodi());
            codiFarmacies.add(farmacies.get(i).getCodi());
        }
        // Center position of the map: the last població LatLon
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // The new target Fragment
        InfoFarmaciaFragment infoFarmaciaFragment = new InfoFarmaciaFragment();
        //Getting the number of pharmacy to send to the other activity
        Bundle bundle = new Bundle();
        // Getting the PK of the pharmacy related with the marker
        // The number N from the marker code mN
        Integer number = Integer.parseInt(marker.getId().substring(1));
        bundle.putString("codiFarmacia", codiFarmacies.get(number));
        bundle.putString("Poblacio", marker.getId());
        infoFarmaciaFragment.setArguments(bundle);
        // Similar to Intents and startActivity but for Fragments
        FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        // Puting the targed Fragment into the container
        fragmentTransaction.replace(container.getId(), infoFarmaciaFragment);
        fragmentTransaction.commit();
        return false;
    }
}
