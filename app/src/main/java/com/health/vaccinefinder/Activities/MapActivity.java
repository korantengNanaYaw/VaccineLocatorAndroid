package com.health.vaccinefinder.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.health.vaccinefinder.DataBase.FaceVcenter;
import com.health.vaccinefinder.DataBase.KilometerSorter;
import com.health.vaccinefinder.DataBase.Vcenters;
import com.health.vaccinefinder.R;
import com.health.vaccinefinder.Utilities.CustomMarker;
import com.health.vaccinefinder.Utilities.GPSTracker;
import com.health.vaccinefinder.Utilities.Helper;
import com.health.vaccinefinder.Utilities.LatLngInterpolator;
import com.health.vaccinefinder.Utilities.MarkerAnimation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MapActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarTitle,providerLabel;

    GPSTracker gpsTracker;
    // Google Map
    private GoogleMap googleMap;
    private HashMap<CustomMarker, Marker> markersHashMap;
    private Iterator<Map.Entry<CustomMarker, Marker>> iter;
    private CameraUpdate cu;
    private CustomMarker customMarkerOne, customMarkerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

gpsTracker = new GPSTracker(MapActivity.this);
        setToolBar("Map View");
        try {
            // Loading map
            initilizeMap();
            initializeUiSettings();
            initializeMapLocationSettings();
            initializeMapTraffic();
            initializeMapType();
            initializeMapViewSettings();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.settings:
                //your code here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setToolBar(String Title){
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        toolbarTitle= (TextView) findViewById(R.id.toolbarTitle);
        toolbarTitle.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbarTitle.setText(Title);

        TextView toolbarnotify=(TextView)findViewById(R.id.toolbarnotify);
        toolbarnotify.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    protected Marker createMarker(Vcenters vcenters,GoogleMap googleMap ) {

        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
       // map.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

        return googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(vcenters.getLongitude()),
                Double.parseDouble(vcenters.getLatitude())))
                .anchor(0.5f, 0.5f).title(vcenters.getFacility())

                .snippet(vcenters.getDistrict()+""+vcenters.getRegion()));

    }

    private void initilizeMap() {

        googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        // check if map is created successfully or not
        if (googleMap == null) {
            Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
        }

        (findViewById(R.id.map)).getViewTreeObserver().addOnGlobalLayoutListener(
                new android.view.ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        // gets called after layout has been done but before
                        // display
                        // so we can get the height then hide the view
                        if (android.os.Build.VERSION.SDK_INT >= 16) {
                            (findViewById(R.id.map)).getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            (findViewById(R.id.map)).getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
                        setCustomMarkerOnePosition();
                       // setCustomMarkerTwoPosition();
                        // plotMarkers(markerList);
                    }
                });
    }

    void setCustomMarkerOnePosition() {
       // customMarkerOne = new CustomMarker("markerOne", 40.7102747, -73.9945297);


        for(FaceVcenter it : getFakeFacilitiesNearest()){

            String description = it.getDistrict() + ":" + it.getFacility() ;
            customMarkerOne = new CustomMarker(it.getFacility(),Double.parseDouble(it.getLongitude()),  Double.parseDouble(it.getLatitude()),description);

            addMarker(customMarkerOne);
        }

    }

    public List<FaceVcenter> getFakeFacilitiesNearest(){

        List<FaceVcenter> faceVcenterlist = new ArrayList<>();
        Helper helper=new Helper();
        FaceVcenter vcenter = new FaceVcenter();
        for (Vcenters center : Vcenters.getListOfBeneficiary()){
            vcenter = new FaceVcenter();

            //  int id= obj.getInt("id");

            vcenter.setId(""+center.getId());

            vcenter.setRegion(center.getRegion());


            vcenter.setDistrict(center.getDistrict());



            vcenter.setSubdistrict(center.getSubdistrict());



            vcenter.setFacility(center.getFacility());



            vcenter.setLatitude(center.getLatitude()); //replacedString[1]
            vcenter.setLongitude(center.getLongitude()); //replacedString[0]

            //  Log.d("from cordinates","latitude : "+ replacedString[0] + " longitude : " + replacedString[1]);


            vcenter.setBcg(center.getBcg());


            vcenter.setOpv(center.getOpv());


            vcenter.setPenta(center.getPenta());


            vcenter.setPneumo(center.getPneumo());


            vcenter.setRota(center.getRota());



            vcenter.setMeasles_rubella(center.getMeasles_rubella());


            vcenter.setYellow_fever(center.getYellow_fever());


            vcenter.setMeningitis_a(center.getMeningitis_a());


            vcenter.setVitamin_a_dose(center.getVitamin_a_dose());


            vcenter.setNutrition_services(center.getNutrition_services());



            vcenter.setPhone(center.getPhone());



            vcenter.setEmail(center.getEmail());


            int kilometers = helper.calculateDistanceInKilometer(gpsTracker.getLatitude(),gpsTracker.getLongitude(),Double.parseDouble(center.getLatitude()),Double.parseDouble(center.getLongitude()));



            vcenter.setKilometers(kilometers);


            faceVcenterlist.add(vcenter);


        }


        List<FaceVcenter> fakestVcenterlist = new ArrayList<>();

        for (FaceVcenter faceVcenter :faceVcenterlist){


            if (faceVcenter.getKilometers() < 10 ){

                fakestVcenterlist.add(faceVcenter);

            }

        }
        Collections.sort(fakestVcenterlist,new KilometerSorter());


        return fakestVcenterlist;

    }



    public void startAnimation(View v) {
        animateMarker(customMarkerOne, new LatLng(40.0675716, 40.7297251));
    }

    public void zoomToMarkers(View v) {
        zoomAnimateLevelToFitMarkers(120);
    }

    public void animateBack(View v) {
        animateMarker(customMarkerOne, new LatLng(32.0675716, 27.7297251));
    }

    public void initializeUiSettings() {
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    public void initializeMapLocationSettings() {
        googleMap.setMyLocationEnabled(true);
    }

    public void initializeMapTraffic() {
        googleMap.setTrafficEnabled(true);
    }

    public void initializeMapType() {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void initializeMapViewSettings() {
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(false);

        LatLng nicaragua = new LatLng(gpsTracker.getLatitude(),gpsTracker.getLongitude());


        CameraUpdate center=
                CameraUpdateFactory.newLatLng(nicaragua);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);

    }

    // this is method to help us set up a Marker that stores the Markers we want
    // to plot on the map
    public void setUpMarkersHashMap() {
        if (markersHashMap == null) {
            markersHashMap = new HashMap<CustomMarker, Marker>();
        }
    }

    // this is method to help us add a Marker into the hashmap that stores the
    // Markers
    public void addMarkerToHashMap(CustomMarker customMarker, Marker marker) {
        setUpMarkersHashMap();
        markersHashMap.put(customMarker, marker);
    }

    // this is method to help us find a Marker that is stored into the hashmap
    public Marker findMarker(CustomMarker customMarker) {
        iter = markersHashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry mEntry = (Map.Entry) iter.next();
            CustomMarker key = (CustomMarker) mEntry.getKey();
            if (customMarker.getCustomMarkerId().equals(key.getCustomMarkerId())) {
                Marker value = (Marker) mEntry.getValue();
                return value;
            }
        }
        return null;
    }

    // this is method to help us add a Marker to the map
    public void addMarker(CustomMarker customMarker) {
        MarkerOptions markerOption = new MarkerOptions().position(
                new LatLng(customMarker.getCustomMarkerLongitude(),customMarker.getCustomMarkerLatitude())).icon(BitmapDescriptorFactory.defaultMarker()).title(customMarker.getDescription()).snippet("30 miles from here");

        Marker newMark = googleMap.addMarker(markerOption);
        addMarkerToHashMap(customMarker, newMark);
    }

    // this is method to help us remove a Marker
    public void removeMarker(CustomMarker customMarker) {
        if (markersHashMap != null) {
            if (findMarker(customMarker) != null) {
                findMarker(customMarker).remove();
                markersHashMap.remove(customMarker);
            }
        }
    }

    // this is method to help us fit the Markers into specific bounds for camera
    // position
    public void zoomAnimateLevelToFitMarkers(int padding) {
        LatLngBounds.Builder b = new LatLngBounds.Builder();
        iter = markersHashMap.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry mEntry = (Map.Entry) iter.next();
            CustomMarker key = (CustomMarker) mEntry.getKey();
            LatLng ll = new LatLng(key.getCustomMarkerLatitude(), key.getCustomMarkerLongitude());
            b.include(ll);
        }
        LatLngBounds bounds = b.build();

        // Change the padding as per needed
       // cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        //googleMap.animateCamera(cu);


    }

    // this is method to help us move a Marker.
    public void moveMarker(CustomMarker customMarker, LatLng latlng) {
        if (findMarker(customMarker) != null) {
            findMarker(customMarker).setPosition(latlng);
            customMarker.setCustomMarkerLatitude(latlng.latitude);
            customMarker.setCustomMarkerLongitude(latlng.longitude);
        }
    }

    // this is method to animate the Marker. There are flavours for all Android
    // versions
    public void animateMarker(CustomMarker customMarker, LatLng latlng) {
        if (findMarker(customMarker) != null) {

            LatLngInterpolator latlonInter = new LatLngInterpolator.LinearFixed();
            latlonInter.interpolate(20,
                    new LatLng(customMarker.getCustomMarkerLatitude(), customMarker.getCustomMarkerLongitude()), latlng);

            customMarker.setCustomMarkerLatitude(latlng.latitude);
            customMarker.setCustomMarkerLongitude(latlng.longitude);

            if (android.os.Build.VERSION.SDK_INT >= 14) {
                MarkerAnimation.animateMarkerToICS(findMarker(customMarker), new LatLng(customMarker.getCustomMarkerLatitude(),
                        customMarker.getCustomMarkerLongitude()), latlonInter);
            } else if (android.os.Build.VERSION.SDK_INT >= 11) {
                MarkerAnimation.animateMarkerToHC(findMarker(customMarker), new LatLng(customMarker.getCustomMarkerLatitude(),
                        customMarker.getCustomMarkerLongitude()), latlonInter);
            } else {
                MarkerAnimation.animateMarkerToGB(findMarker(customMarker), new LatLng(customMarker.getCustomMarkerLatitude(),
                        customMarker.getCustomMarkerLongitude()), latlonInter);
            }
        }
    }
}
