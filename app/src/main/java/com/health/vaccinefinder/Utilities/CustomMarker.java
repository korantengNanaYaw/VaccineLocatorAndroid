package com.health.vaccinefinder.Utilities;

/**
 * Created by smsgh on 21/02/2017.
 */

public class CustomMarker {

    private String id;
    private  String Description;
    private Double latitude;
    private Double longitude;

    public CustomMarker(String id, Double latitude, Double longitude,String Description) {

        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.Description = Description;
    }

    public CustomMarker() {
        this.id = "";
        this.latitude = 0.0;
        this.longitude = 0.0;
        Description= "";
    }

    public String getCustomMarkerId() {
        return id;
    }

    public void setCustomMarkerId(String id) {
        this.id = id;
    }

    public Double getCustomMarkerLatitude() {
        return latitude;
    }

    public void setCustomMarkerLatitude(Double mLatitude) {
        this.latitude = mLatitude;
    }

    public Double getCustomMarkerLongitude() {
        return longitude;
    }

    public void setCustomMarkerLongitude(Double mLongitude) {
        this.longitude = mLongitude;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}