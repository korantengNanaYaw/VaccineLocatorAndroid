package com.health.vaccinefinder.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.health.vaccinefinder.DataBase.Vcenters;
import com.health.vaccinefinder.R;

import java.util.ArrayList;
import java.util.List;

public class FacilityDetails extends AppCompatActivity {


    ListView listView;
    Toolbar toolbar;
    TextView toolbarTitle,providerLabel;
    Vcenters vcenters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_details);

        setToolBar("Facility Detail");

        initView();
    }

    private void setToolBar(String Title){
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        toolbarTitle= (TextView) findViewById(R.id.toolbarTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbarTitle.setText(Title);

        TextView toolbarnotify=(TextView)findViewById(R.id.toolbarnotify);
        toolbarnotify.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initView(){


        Bundle extras = getIntent().getExtras();
        if (extras != null) {


            String facility_ID = extras.getString("facility_ID");

            String distanceMiles = extras.getString("distanceTxt");

            Log.d("facilityID",facility_ID);
             vcenters = Vcenters.load(Vcenters.class,Integer.parseInt(facility_ID));
            Log.d("facilityID",vcenters.getFacility());

            TextView txtFacilityName = (TextView) findViewById(R.id.tctFacilityName);
             txtFacilityName.setText("" + vcenters.getFacility());


            TextView region = (TextView) findViewById(R.id.tctregion);
            region.setText("Region : " + vcenters.getRegion());


            TextView district = (TextView) findViewById(R.id.txtDistrict);
            district.setText("District : " + vcenters.getDistrict());


            TextView tctSubdistrict = (TextView) findViewById(R.id.tctSubdistrict);
            tctSubdistrict.setText("Sub-District : " + vcenters.getSubdistrict());


            TextView txtphone = (TextView) findViewById(R.id.txtphone);
            txtphone.setText("Phone : " + vcenters.getPhone());


            TextView txtemail = (TextView) findViewById(R.id.txtemail);
            txtemail.setText("Email : " + vcenters.getEmail());


            TextView txtdistance = (TextView) findViewById(R.id.txtmiles);
            txtdistance.setText("Distance : " +distanceMiles);



            listView=(ListView)findViewById(R.id.servicesOff);


            List<String> services = new ArrayList<String>();
            services.add("BCG : "+ vcenters.getBcg());
            services.add("OPV  : "+vcenters.getOpv());
            services.add("Penta : "+vcenters.getPenta());
            services.add("Pneumo : "+ vcenters.getPneumo());
            services.add("Rota : "+vcenters.getRota());
            services.add("Measles Rubella : "+vcenters.getMeasles_rubella());
            services.add("Yellow Fever : "+vcenters.getYellow_fever());
            services.add("Meningitis A : "+vcenters.getMeningitis_a());
            services.add("Vitamin A Dose : "+vcenters.getVitamin_a_dose());
            services.add("Nutrition Services : "+vcenters.getNutrition_services());


            // This is the array adapter, it takes the context of the activity as a
            // first parameter, the type of list view as a second parameter and your
            // array as a third parameter.
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    services );

            listView.setAdapter(arrayAdapter);


        }


        Button butReservation = (Button)findViewById(R.id.butReservation);
        butReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FacilityDetails.this,ScheduleAppointment.class);
                startActivity(intent);
            }
        });

        Button getdirection =  (Button)findViewById(R.id.butDirection);
        getdirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse("geo:"+vcenters.getLatitude()+","+vcenters.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }else {

                    Toast.makeText(FacilityDetails.this,"You dont have Working",Toast.LENGTH_LONG).show();
                }

            }
        });
      //  (TextView)findViewById(R.id.txtFacilityName).setText


    }
}
