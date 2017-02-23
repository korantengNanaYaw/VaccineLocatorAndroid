package com.health.vaccinefinder.Utilities;

import android.location.Location;

/**
 * Created by smsgh on 21/02/2017.
 */

public class Helper {


public static int getMetersToMiles(double meters){


    double inches = (39.370078 * meters);
    int miles = (int) (inches / 63360);

    return miles;
}
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    public int  calculateDistanceInKilometer(double userLat, double userLng,
                                            double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }
}
