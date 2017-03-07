package com.health.vaccinefinder.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
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
import com.android.volley.toolbox.StringRequest;
import com.health.vaccinefinder.DataBase.Users;
import com.health.vaccinefinder.R;
import com.health.vaccinefinder.Utilities.Dialogs;
import com.health.vaccinefinder.Volley.APIrequest;
import com.health.vaccinefinder.Volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;

public class Register extends AppCompatActivity {

    Spinner gender;
    EditText fullname,username,password,dob;
    Button registerBut;


    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    Dialogs dialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        initViews();
    }

    private void initViews() {

        dialogs= new Dialogs(Register.this);

        fullname=(EditText)findViewById(R.id.input_fullname);
        username=(EditText)findViewById(R.id.input_username);
        password=(EditText)findViewById(R.id.input_password);
        dob=(EditText)findViewById(R.id.input_dob);
        gender=(Spinner)findViewById(R.id.spinner2);


        registerBut=(Button)findViewById(R.id.registerBut);

        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(fullname.getText().toString().isEmpty() == false && username.getText().toString().isEmpty() == false && password.getText().toString().isEmpty()==false && dob.getText().toString().isEmpty()==false){

                    RegisterNewUser();

                }else{


                    Toast.makeText(Register.this,"Some Required Fields are empty",Toast.LENGTH_LONG).show();

                }


            }
        });
    }




    private void RegisterNewUser(){


        final ProgressDialog pDialog  = new ProgressDialog(this);
        pDialog.setMessage("Registering ...");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //  pDialog.setIcon(R.drawable.ic_synce);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();



        final HashMap<String, String> params = new HashMap<String, String>();







        params.put("fullname",fullname.getText().toString());
        params.put("email",""+ username.getText().toString());
        params.put("password", password.getText().toString());
        params.put("gender", gender.getSelectedItem().toString());
        params.put("dob", dob.getText().toString());

        volleySingleton= VolleySingleton.getsInstance();
        requestQueue=VolleySingleton.getRequestQueue();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                APIrequest.registerNewUser,
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

                                dialogs.SimpleWarningAlertDialog("Registration Failed", "Failed to register User,Please try again").show();

                            }else{

                                Users users =Users.load(Users.class,1);

                                if(users == null){

                                    users= new Users();
                                }

                                JSONObject jresponse = response.getJSONObject("user");
                              //  JSONObject jresponse = new JSONObject(responseString);

                                users.setFullname(jresponse.getString("fullname"));
                                users.setGender(jresponse.getString("gender"));
                                users.setUserid(jresponse.getInt("id"));
                                users.setUsername(jresponse.getString("email"));
                                users.setDob(jresponse.getString("dob"));

                                users.save();

                                Intent intent = new Intent(Register.this,MainActivity.class);
                                startActivity(intent);


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
