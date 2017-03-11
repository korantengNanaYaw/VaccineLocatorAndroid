package com.health.vaccinefinder.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.health.vaccinefinder.DataBase.Appointments;
import com.health.vaccinefinder.DataBase.Users;
import com.health.vaccinefinder.R;
import com.health.vaccinefinder.Utilities.Dialogs;
import com.health.vaccinefinder.Volley.APIrequest;
import com.health.vaccinefinder.Volley.VolleySingleton;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class ScheduleAppointment extends AppCompatActivity {

    CalendarView calendarView;
    Toolbar toolbar;
    TextView toolbarTitle,providerLabel;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button pickDate,pickTime,sendButton;
    Bundle extras;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    String dateString;
    String timeString;
    String facility_ID;

    Dialogs dialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_appointment);
        dialogs= new Dialogs(ScheduleAppointment.this);
        setToolBar("Schedule Appointment");

        initView();



        extras = getIntent().getExtras();
        if (extras != null) {


            facility_ID = extras.getString("facility_ID");


        }
    }

    private void initView() {


        pickDate = (Button)findViewById(R.id.pickdate);
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDatePicker();
            }
        });


        pickTime = (Button)findViewById(R.id.picktime);
        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker();
            }
        });



        sendButton=(Button)findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BookAppointment();

            }
        });



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


    public void openDatePicker() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear  = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay   = c.get(Calendar.DAY_OF_MONTH);
        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Log.d("OpenDate", "DATE SELECTED "+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        dateString = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        pickDate.setText(dateString);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void openTimePicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour            = c.get(Calendar.HOUR_OF_DAY);
        mMinute          = c.get(Calendar.MINUTE);
        //launch timepicker modal
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.d("logged", "TIME SELECTED "+hourOfDay + "-" + minute + "-");

                        timeString = hourOfDay + "-" + minute + "-";
                      //  pickDate.setText(dateString);

                        pickTime.setText(timeString);
                        //PUT YOUR LOGIC HERE
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void BookAppointment(){


        EditText nameEdt=(EditText)findViewById(R.id.input_fullname);
        EditText phoneEdt=(EditText)findViewById(R.id.input_phone);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        final String date = dateString;
        final String time = timeString;

        final String name = nameEdt.getText().toString();
        final String phone = phoneEdt.getText().toString();
        final String service =  spinner.getSelectedItem().toString();

        final String facilityID = facility_ID;




        final ProgressDialog pDialog  = new ProgressDialog(this);
        pDialog.setMessage("Scheduling appointment ...");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();



        final HashMap<String, String> params = new HashMap<String, String>();








        params.put("date",date);
        params.put("time",time);
        params.put("name",name);
        params.put("phone",phone);
        params.put("service",service);
        params.put("facilityID",facilityID);






        volleySingleton= VolleySingleton.getsInstance();
        requestQueue=VolleySingleton.getRequestQueue();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                APIrequest.appointments,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    //Log.d("Params",params+"");
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.hide();
                        try {

                            Log.d("results",response.toString());


                            String status = response.getString("status");

                            if(status.equals("failed")){

                                dialogs.SimpleWarningAlertDialog(" Failed Transmit Data", "Failed ").show();

                            }else{


                                Appointments appointments = new Appointments();
                                appointments.setDate(date);
                                appointments.setFacilityID(facilityID);
                                appointments.setName(name);
                                appointments.setPhone(phone);
                                appointments.setTime(time);
                                appointments.setService(service);
                                appointments.save();


                                dialogs.SimpleWarningAlertDialog("Appointment Booked", "Suucess ").show();

                            }




                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        //  Message.messageShort(MyApplication.getAppContext(),""+tokenValue+"\n"+response.toString()+"\n"+booleaner);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)   {
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
        }) {

        };
        int socketTimeout = 480000000;//8 minutes - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        requestQueue.add(request);
        Log.d("oxinbo","Server Logs"+params.toString());
    }


    }







