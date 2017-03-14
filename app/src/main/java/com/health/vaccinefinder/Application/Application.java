package com.health.vaccinefinder.Application;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.health.vaccinefinder.DataBase.Appointments;
import com.health.vaccinefinder.DataBase.Users;
import com.health.vaccinefinder.DataBase.Vcenters;

/**
 * Created by smsgh on 20/02/2017.
 */

public class Application extends android.app.Application{
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();




        try{



            initializeDB();
        //   InitializeDBTables();


        }catch (Exception e){

            e.printStackTrace();
        }




    }

    public static Context getAppContext() {
        return context;
    }
    protected void initializeDB() {
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);


        configurationBuilder.addModelClasses(Vcenters.class);
        configurationBuilder.addModelClass(Users.class);
        configurationBuilder.addModelClass(Appointments.class);

        ActiveAndroid.initialize(configurationBuilder.create());
    }


/**
    protected void InitializeDBTables(){


        ActiveAndroid.beginTransaction();
        try {

//[-1.34982,6.22393]
           Vcenters vcenters = new Vcenters();
            vcenters.setDistrict("Adansi North");
            vcenters.setRegion("Ashanti");
            vcenters.setSubdistrict("Anhwiaso");
            vcenters.setFacility("Anwoma CHPS Compound");
            vcenters.setLatitude("-1.34982");
            vcenters.setLongitude("6.22393");
            vcenters.save();

//[-1.44854,6.11048]
            vcenters = new Vcenters();
            vcenters.setDistrict("Asas North");
            vcenters.setRegion("Asasa");
            vcenters.setSubdistrict("asasa");
            vcenters.setFacility("Anwoma s asa");
            vcenters.setLatitude("-1.44854");
            vcenters.setLongitude("6.11048");
            vcenters.save();



            ActiveAndroid.setTransactionSuccessful();


        }catch (Exception e){

            e.printStackTrace();
        }

        finally {
            ActiveAndroid.endTransaction();

        }


    }
    **/
}
