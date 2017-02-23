package com.health.vaccinefinder.DataBase;

import java.util.Comparator;

/**
 * Created by smsgh on 23/02/2017.
 */

public class  KilometerSorter implements Comparator<FaceVcenter> {
    @Override
    public int compare(FaceVcenter faceVcenter, FaceVcenter t1) {
        int returnVal = 0;

        if (faceVcenter.getKilometers() < t1.getKilometers()) {
            returnVal = -1;
        } else if (faceVcenter.getKilometers() > t1.getKilometers()) {
            returnVal = 1;
        } else if (faceVcenter.getKilometers() == t1.getKilometers()) {
            returnVal = 0;
        }
        return returnVal;
    }
}