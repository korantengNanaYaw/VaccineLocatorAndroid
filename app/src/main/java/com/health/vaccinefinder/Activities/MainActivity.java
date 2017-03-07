package com.health.vaccinefinder.Activities;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.health.vaccinefinder.Adapters.RowAdapter;
import com.health.vaccinefinder.Adapters.RowAdapterForFake;
import com.health.vaccinefinder.Application.Application;
import com.health.vaccinefinder.DataBase.FaceVcenter;
import com.health.vaccinefinder.DataBase.KilometerSorter;
import com.health.vaccinefinder.DataBase.Users;
import com.health.vaccinefinder.DataBase.Vcenters;
import com.health.vaccinefinder.DialogFragment.FacilityDetailFragment;
import com.health.vaccinefinder.R;
import com.health.vaccinefinder.Utilities.Dialogs;
import com.health.vaccinefinder.Utilities.FallbackLocationTracker;
import com.health.vaccinefinder.Utilities.GPSTracker;
import com.health.vaccinefinder.Utilities.Helper;
import com.health.vaccinefinder.Utilities.ProviderLocationTracker;
import com.health.vaccinefinder.Volley.APIrequest;
import com.health.vaccinefinder.Volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity implements FacilityDetailFragment.OnFragmentInteractionListener{

    RecyclerView homeMenusRecycler;
    FloatingActionButton actionButton ;
    GPSTracker gpsTracker;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    Dialogs dialogs;
    FallbackLocationTracker fallbackLocationTracker;

    Toolbar toolbar;
    TextView toolbarTitle,providerLabel,welcome;
    private static String baseURL = "https://webpay.etranzactgh.com/MobileGate/MessageReceiver?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialogs=new Dialogs(this);
        setUpGps();
        setFloatAction();
        setToolBar("Vaccine Centers");


        welcome=(TextView)findViewById(R.id.welcome);
        welcome.setText("Welcome : " + Users.load(Users.class,1).getFullname());


      Log.d("fallbackLatlong", gpsTracker.getLatitude() + "" + gpsTracker.getLongitude()) ;

    }


    public void loadInit(){

        if(Vcenters.getListOfBeneficiary().size() == 0 || Vcenters.getListOfBeneficiary() == null){

            getAlllfacilitiesIfDataBaseEmpty(APIrequest.getAllFacilities);


        }else{


        }

       // setRecycleView();
        setRecycleView(getFakeFacilitiesNearest());
        Log.d("faciliteis Size", Vcenters.getListOfBeneficiary().size()+"");


    }
    public void setRecycleView(){

        homeMenusRecycler=(RecyclerView)findViewById(R.id.vacineList);
        RecyclerView.LayoutManager mlayout=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);

        RowAdapter billersAdapter=new RowAdapter(Application.getAppContext(), Vcenters.getListOfBeneficiary(),gpsTracker);
        homeMenusRecycler.setAdapter(billersAdapter);
        homeMenusRecycler.setLayoutManager(mlayout);

        homeMenusRecycler.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this, homeMenusRecycler, new ClickListner() {
            @Override
            public void onClick(View view, int position) {


                TextView facilityIDView =(TextView)view.findViewById(R.id.txthiddenId);
                String facilityID = facilityIDView.getText().toString();

                TextView distanceTxtView =(TextView)view.findViewById(R.id.txtMiles);
                String distanceTxt = distanceTxtView.getText().toString();

                Log.d("facilityID",facilityID);
                Intent intent = new Intent(MainActivity.this,FacilityDetails.class);
                intent.putExtra("facility_ID", facilityID);
                intent.putExtra("distanceTxt", distanceTxt);

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        /*
        *  BeneficiaryDialog generic=BeneficiaryDialog.newInstance("","");
                generic.setCancelable(false);
                ShowDialog(generic,"");
        *
        * */

    }
    public void setRecycleView(List<FaceVcenter> vcenterList){

        homeMenusRecycler=(RecyclerView)findViewById(R.id.vacineList);
        RecyclerView.LayoutManager mlayout=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);

        RowAdapterForFake billersAdapter=new RowAdapterForFake(Application.getAppContext(), vcenterList,gpsTracker);
        homeMenusRecycler.setAdapter(billersAdapter);
        homeMenusRecycler.setLayoutManager(mlayout);

        homeMenusRecycler.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this, homeMenusRecycler, new ClickListner() {
            @Override
            public void onClick(View view, int position) {


                TextView facilityIDView =(TextView)view.findViewById(R.id.txthiddenId);
                String facilityID = facilityIDView.getText().toString();

                TextView distanceTxtView =(TextView)view.findViewById(R.id.txtMiles);
                String distanceTxt = distanceTxtView.getText().toString();

                Log.d("facilityID",facilityID);
                Intent intent = new Intent(MainActivity.this,FacilityDetails.class);
                intent.putExtra("facility_ID", facilityID);
                intent.putExtra("distanceTxt", distanceTxt);

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        /*
        *  BeneficiaryDialog generic=BeneficiaryDialog.newInstance("","");
                generic.setCancelable(false);
                ShowDialog(generic,"");
        *
        * */

    }

    public void setRecycleView(String region){

        homeMenusRecycler=(RecyclerView)findViewById(R.id.vacineList);
        RecyclerView.LayoutManager mlayout=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);

        RowAdapter billersAdapter=new RowAdapter(Application.getAppContext(), Vcenters.getFacilitiesByRegion(region),gpsTracker);
        homeMenusRecycler.setAdapter(billersAdapter);
        homeMenusRecycler.setLayoutManager(mlayout);

        homeMenusRecycler.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this, homeMenusRecycler, new ClickListner() {
            @Override
            public void onClick(View view, int position) {


                TextView facilityIDView =(TextView)view.findViewById(R.id.txthiddenId);
                String facilityID = facilityIDView.getText().toString();

                TextView distanceTxtView =(TextView)view.findViewById(R.id.txtMiles);
                String distanceTxt = distanceTxtView.getText().toString();

                Log.d("facilityID",facilityID);
                Intent intent = new Intent(MainActivity.this,FacilityDetails.class);
                intent.putExtra("facility_ID", facilityID);
                intent.putExtra("distanceTxt", distanceTxt);

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        /*
        *  BeneficiaryDialog generic=BeneficiaryDialog.newInstance("","");
                generic.setCancelable(false);
                ShowDialog(generic,"");
        *
        * */

    }
    private void setFloatAction() {


        actionButton=(FloatingActionButton)findViewById(R.id.fab);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setUpGps(){

        gpsTracker = new GPSTracker(MainActivity.this);



        if(gpsTracker.canGetLocation() == false){

            gpsTracker.showSettingsAlert();

        }else{

            loadInit();

            //Toast.makeText(MainActivity.this,gpsTracker.getLatitude() + "," + gpsTracker.getLongitude(),Toast.LENGTH_LONG).show();
            Log.d("fMainactivityLatlong", gpsTracker.getLatitude() + "," + gpsTracker.getLongitude()) ;

        }

    }
    private void setToolBar(String Title){
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        toolbarTitle= (TextView) findViewById(R.id.toolbarTitle);
        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
       // toolbarTitle.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbarTitle.setText(Title);

        TextView toolbarnotify=(TextView)findViewById(R.id.toolbarnotify);
        toolbarnotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog();
            }
        });
       // toolbarnotify.setVisibility(View.GONE);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void getAlllfacilitiesIfDataBaseEmpty(String finalUrl){


        final ProgressDialog pDialog  = new ProgressDialog(this);
        pDialog.setMessage("Getting facilities...");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //  pDialog.setIcon(R.drawable.ic_synce);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        // final String url=getUrlVerifyAccountNumber(account,phonenum);
        Log.d("finalUrl",finalUrl);



        volleySingleton= VolleySingleton.getsInstance();
        requestQueue=VolleySingleton.getRequestQueue();

        StringRequest request = new StringRequest(Request.Method.GET, finalUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();

                        extractJsonArrayToDataBase(response);
                       // Log.d("payload", "response " + response.toString());

                        //Toast.makeText(Login.this,res)

                        /*new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                .setCustomImage(R.mipmap.ic_launcher)

                                .setTitleText("Hello there !!")
                                .setContentText("Success")
                                .setConfirmText("Okay")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();


                                    }
                                })
                                .show();*/


                        //  dialogs.SimpleWarningAlertDialog(response, "Success Message").show();




                        //dialogs.SimpleWarningAlertDialog(response, "Success Message");
                        // response
                        /**
                         VerifyModel verifyModel =new VerifyModel(response.trim());
                         Log.d("volley", verifyModel.getAccountNumber() + "  " + verifyModel.getResponseCode() + "  " +
                         "" + verifyModel.getOtherInfo());

                         if(verifyModel.getResponseCode().equals("00")){
                         //Success Dialog began





                         }else{

                         // VerifyPhoneNumber(phonenum);

                         dialogs.SimpleWarningAlertDialog(""+verifyModel.getOtherInfo(),"Verification Failed").show();

                         }
                         **/
                        // dialogs.ThreeButtonAlertDialog(""+verifyModel.getOtherInfo(),"Message").show();

                        // VerifyPhoneNumber(phonenum);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        dialogs.SimpleWarningAlertDialog("Transmission Error", "Connection Failed").show();
                        Log.d("volley.Response", error.toString());



                        pDialog.dismiss();
                        if (error instanceof TimeoutError) {
                            dialogs.SimpleWarningAlertDialog("Network Slacking", "Time Out Error").show();
                            Log.d("volley", "NoConnectionError.....TimeoutError..");


                            //     dialogs.SimpleWarningAlertDialog("Network Slacking", "Time Out Error");



                        } else if(error instanceof NoConnectionError){

                            dialogs.SimpleWarningAlertDialog("No Internet Connections Detected", "No Internet Connection").show();

                        }


                        else if (error instanceof AuthFailureError) {
                            //  Log.d("volley", "AuthFailureError..");
                            dialogs.SimpleWarningAlertDialog("Authentication Failure","AuthFailureError").show();


                        } else if (error instanceof ServerError) {
                            dialogs.SimpleWarningAlertDialog("Server Malfunction", "Server Error").show();

                        } else if (error instanceof NetworkError) {
                            dialogs.SimpleWarningAlertDialog("Network Error", "Network Error").show();

                        } else if (error instanceof ParseError) {
                            dialogs.SimpleWarningAlertDialog("Parse Error","Parse Error").show();
                        }

                    }
                }
        ) {
            /**  @Override
            protected Map<String, String> getParams()
            {
            Map<String, String>  params = new HashMap<String, String>();
            params.put("name", "Alif");
            params.put("domain", "http://itsalif.info");

            return params;
            }**/
        };




        int socketTimeout = 240000000;//4 minutes - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        requestQueue.add(request);


        //Log.d("oxinbo", "Server params" + params.toString());
    }

    private void extractJsonArrayToDataBase(String response){
        List<Vcenters> facilities = new ArrayList<Vcenters>();
        Vcenters vcenter = new Vcenters();

        try {


            JSONArray dataArray= new JSONArray(response);


            for (int i = 0 ; i < dataArray.length(); i++) {

                vcenter = new Vcenters();
                JSONObject obj = dataArray.getJSONObject(i);

              //  int id= obj.getInt("id");
                String region = obj.getString("region");
                vcenter.setRegion(region);

                String district = obj.getString("district");
                vcenter.setDistrict(district);


                String sub_district = obj.getString("sub_district");
                vcenter.setSubdistrict(sub_district);


                String facility = obj.getString("facility");
                vcenter.setFacility(facility);

                String coordinates = obj.getString("coordinates");
                String  result = coordinates.replaceAll("[\\[\\](){}]","");
                String[] replacedString = result.split(",");


                vcenter.setLatitude(replacedString[1]); //replacedString[1]
                vcenter.setLongitude(replacedString[0]); //replacedString[0]

              //  Log.d("from cordinates","latitude : "+ replacedString[0] + " longitude : " + replacedString[1]);

                String bcg = obj.getString("bcg");
                vcenter.setBcg(bcg);

                String opv = obj.getString("opv");
                vcenter.setOpv(opv);

                String penta = obj.getString("penta");
                vcenter.setPenta(penta);

                String pneumo = obj.getString("pneumo");
                vcenter.setPneumo(pneumo);

                String rota = obj.getString("rota");
                vcenter.setRota(rota);


                String measles_rubella = obj.getString("measles_rubella");
                vcenter.setMeasles_rubella(measles_rubella);

                String yellow_fever = obj.getString("yellow_fever");
                vcenter.setYellow_fever(yellow_fever);

                String meningitis_a = obj.getString("meningitis_a");
                vcenter.setMeningitis_a(meningitis_a);

                String vitamin_a_dose = obj.getString("vitamin_a_dose");
                vcenter.setVitamin_a_dose(vitamin_a_dose);

                String nutrition_services = obj.getString("nutrition_services");
                vcenter.setNutrition_services(nutrition_services);


                String phone = obj.getString("phone").isEmpty() == true ? "" : obj.getString("phone");
                vcenter.setPhone(phone);


                String email = obj.getString("email").isEmpty() == true ? "" : obj.getString("email");
                vcenter.setEmail(email);

                facilities.add(vcenter);



            }

            ActiveAndroid.beginTransaction();
            try
            {

                for(Vcenters center : facilities){


                 Long id =   center.save();


                    Log.d("facility ID", "id"+id);

                }



                ActiveAndroid.setTransactionSuccessful();
            }
            finally {
                ActiveAndroid.endTransaction();

                setRecycleView(getFakeFacilitiesNearest());
              //  setRecycleView();
            }


            Log.d("facilities Size",""+facilities.size());

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public  List<FaceVcenter> getFakeFacilitiesNearest(){

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
    private void ShowDialog(DialogFragment fragment, String tag){

        FragmentManager fm = this.getFragmentManager();

        fragment.show(fm,tag);

    }

    private void AlertDialog(){

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Sort By Region");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item);

        arrayAdapter.add("Nearest");
        for (String region :Vcenters.getFacilitiesByRegion()){

            arrayAdapter.add(region);

        }



        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);

                if (strName.equals("Nearest")){

              /*      Collections.sort(getFakeFacilitiesNearest(), new Comparator<FaceVcenter>() {
                        public int compare(FaceVcenter a, FaceVcenter b) {
                            if (a.getKilometers() == b.getKilometers())
                                return a.getName().compareTo(b.getName());
                            return a.getPrice() > b.getPrice() ? 1 : a.getPrice() < b.getPrice() ? -1 : 0;
                        }
                    });*/

                    setRecycleView(getFakeFacilitiesNearest());





                }else {
                    setRecycleView(strName);
                }
            }
        });
        builderSingle.show();

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private ClickListner clickListner;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListner clickListner){

            this.clickListner=clickListner;

            gestureDetector= new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    View child= recyclerView.findChildViewUnder(e.getX(),e.getY());

                    if(child !=null & clickListner !=null){


                        clickListner.onClick(child, recyclerView.getChildPosition(child));

                    }

                    return super.onSingleTapUp(e);
                }

                @Override
                public void onLongPress(MotionEvent e) {



                    View child= recyclerView.findChildViewUnder(e.getX(),e.getY());

                    if(child !=null & clickListner !=null){


                        clickListner.onLongClick(child,recyclerView.getChildPosition(child));

                    }
                    // super.onLongPress(e);
                }
            });



        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child= rv.findChildViewUnder(e.getX(),e.getY());

            if(child !=null & clickListner !=null && gestureDetector.onTouchEvent(e) ){


                clickListner.onClick(child, rv.getChildPosition(child));

            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    public static  interface ClickListner{
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }
}
