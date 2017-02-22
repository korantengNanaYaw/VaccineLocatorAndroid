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

}
