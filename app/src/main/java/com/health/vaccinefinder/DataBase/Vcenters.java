package com.health.vaccinefinder.DataBase;

import android.widget.ListView;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smsgh on 20/02/2017.
 */



@Table(name="Vcenters")
public class Vcenters  extends Model {

    @Column(name = "region")
    private String region;

    @Column(name = "district")
    private String district;


    @Column(name = "subdistrict")
    private String subdistrict;

    @Column(name = "facility")
    private String facility;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "bcg")
    private String bcg;

    @Column(name = "opv")
    private String opv;

    @Column(name = "penta")
    private String penta;

    @Column(name = "pneumo")
    private String pneumo;

    @Column(name = "rota")
    private String rota;

    @Column(name = "measles_rubella")
    private String measles_rubella;


    @Column(name = "yellow_fever")
    private String yellow_fever;


    @Column(name = "meningitis_a")
    private String meningitis_a;



    @Column(name = "vitamin_a_dose")
    private String vitamin_a_dose;



    @Column(name = "nutrition_services")
    private String nutrition_services;


    @Column(name = "phone")
    private String phone;



    @Column(name = "email")
    private String email;


    /*
    * 	'bcg',
		'opv',
		'penta',
		'pneumo',
		'rota',
		'measles_rubella',
		'yellow_fever',
		'meningitis_a',
		'vitamin_a_dose',
		'nutrition_services',
		'phone',
		'email'
    *
    * */

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubdistrict() {
        return subdistrict;
    }

    public void setSubdistrict(String subdistrict) {
        this.subdistrict = subdistrict;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getBcg() {
        return bcg;
    }

    public void setBcg(String bcg) {
        this.bcg = bcg;
    }

    public String getOpv() {
        return opv;
    }

    public void setOpv(String opv) {
        this.opv = opv;
    }

    public String getPenta() {
        return penta;
    }

    public void setPenta(String penta) {
        this.penta = penta;
    }

    public String getPneumo() {
        return pneumo;
    }

    public void setPneumo(String pneumo) {
        this.pneumo = pneumo;
    }

    public String getRota() {
        return rota;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }

    public String getMeasles_rubella() {
        return measles_rubella;
    }

    public void setMeasles_rubella(String measles_rubella) {
        this.measles_rubella = measles_rubella;
    }

    public String getYellow_fever() {
        return yellow_fever;
    }

    public void setYellow_fever(String yellow_fever) {
        this.yellow_fever = yellow_fever;
    }

    public String getMeningitis_a() {
        return meningitis_a;
    }

    public void setMeningitis_a(String meningitis_a) {
        this.meningitis_a = meningitis_a;
    }

    public String getVitamin_a_dose() {
        return vitamin_a_dose;
    }

    public void setVitamin_a_dose(String vitamin_a_dose) {
        this.vitamin_a_dose = vitamin_a_dose;
    }

    public String getNutrition_services() {
        return nutrition_services;
    }

    public void setNutrition_services(String nutrition_services) {
        this.nutrition_services = nutrition_services;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static List<Vcenters> getListOfBeneficiary(){



        return new Select().from(Vcenters.class).execute();


    }

    public static Vcenters getFacility(int id){



        return Vcenters.load(Vcenters.class,id);


    }

    public static List<Vcenters> getFacilitiesByRegion(String region){



        return new Select().from(Vcenters.class).where("region = ?",region).execute();


    }
    public static List<String> getFacilitiesByRegion(){


        List<Vcenters> categoryList = new Select().distinct().from(Vcenters.class).groupBy("region").execute();

        List<String> regions = new ArrayList<>();

        for (Vcenters c : categoryList){

            regions.add(c.getRegion());
        }


        return regions;

    }
}
