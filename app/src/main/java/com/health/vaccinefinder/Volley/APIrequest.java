package com.health.vaccinefinder.Volley;

/**
 * Created by smsgh on 22/02/2017.
 */

public class APIrequest {


    public static String Base_Url = "https://vacinelocator.herokuapp.com/";
    public static String  getAllFacilities = Base_Url+ "getAllFacilities"  ;
    public static String  registerNewUser = Base_Url+ "registerUser"  ;
    public static String  loginUser = Base_Url+ "loginUser"  ;

    public static String  appointments = Base_Url+ "bookAppointment"  ;
}
