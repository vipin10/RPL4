package com.radiant.rpl.testa;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import radiant.rpl.radiantrpl.R;

public class MainActivity extends AppCompatActivity {

    Spinner yearofbirth,monthofbirth,dateofbirth,education,employment,employer,sector,bankname,state,district;
    EditText input_name,input_last_name,input_mobile_no,input_address1
            ,input_address2,input_pincode,input_aadhar,input_bank_ac,input_ifsc_code,input_bank_username;
ProgressDialog pd;
    String[] banks,states,districts,sectors,employers;
    List<String> bankslist,Statelist,districtlist,sectorlist,employerlist;
    HashMap<String, String> bankdetail = new HashMap<>();
    HashMap<String,String> Statedetail=new HashMap<>();
    HashMap<String,String> districtdetail=new HashMap<>();
    HashMap<String,String> sectordetail=new HashMap<>();
    HashMap<String,String> employerdetail =new HashMap<>();
    Button input_photograph,input_submit;
    String Stateid,Statevalue,bankid,bankvalue,districtid,districtvalue,selectedstatetext,sectorid,sectorvalue,employerid,employervalue;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String yearobirth,monthobirth,dateobirth;
    AwesomeValidation awesomeValidation;
    String gender,eduction1,employment1,employer1,sector1,bankname1,state1,district1,encodedphoto;
    String bankiddd,stateiddd,districtiddd,employeridd,sectoridd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner myspinner = findViewById(R.id.input_layout_gender);
        yearofbirth=findViewById(R.id.input_layout_year);
        monthofbirth=findViewById(R.id.input_layout_month);
        dateofbirth=findViewById(R.id.input_layout_date);
        education=findViewById(R.id.input_layout_Education);
        employment=findViewById(R.id.input_layout_Employment);
        employer=findViewById(R.id.input_layout_Employer);
        sector=findViewById(R.id.input_layout_Sector);
        bankname=findViewById(R.id.input_layout_bankname);
        state=findViewById(R.id.input_layout_State);
        district=findViewById(R.id.input_layout_District);
        input_photograph=findViewById(R.id.input_photograph);
        input_submit=findViewById(R.id.btn_signup);
        input_name=findViewById(R.id.input_name);
        input_last_name=findViewById(R.id.input_last_name);
        input_mobile_no=findViewById(R.id.input_mobile_no);
        input_address1=findViewById(R.id.input_address1);
        input_address2=findViewById(R.id.input_address2);
        input_pincode=findViewById(R.id.input_pincode);
        input_aadhar=findViewById(R.id.input_aadhar);
        input_bank_ac=findViewById(R.id.input_bank_ac);
        input_ifsc_code=findViewById(R.id.input_ifsc_code);
        input_bank_username = findViewById(R.id.input_bank_username);
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);


        awesomeValidation.addValidation(MainActivity.this,R.id.input_name,"[a-zA-Z\\s]+",R.string.err_msg_for_first_name);
        awesomeValidation.addValidation(MainActivity.this,R.id.input_last_name,"[a-zA-Z\\s]+",R.string.err_msg_for_last_name);
        awesomeValidation.addValidation(MainActivity.this,R.id.input_address1,"(.|\\s)*\\S(.|\\s)*",R.string.err_msg_for_address1);
        awesomeValidation.addValidation(MainActivity.this,R.id.input_pincode,"^[0-9]{6}$",R.string.err_msg_pincode);
        awesomeValidation.addValidation(MainActivity.this,R.id.input_bank_ac,"^[0-9]{11,14}$",R.string.err_msg_for_acno);
        awesomeValidation.addValidation(MainActivity.this,R.id.input_ifsc_code,"^[A-Z0-9]{6,12}$",R.string.err_msg_for_ifsc);
        awesomeValidation.addValidation(MainActivity.this,R.id.input_aadhar,"^[0-9]{12}$",R.string.err_msg_foraadhar);
        awesomeValidation.addValidation(MainActivity.this,R.id.input_mobile_no,"^[0-9]{10}$",R.string.err_msg_formobile);
        awesomeValidation.addValidation(MainActivity.this,R.id.input_bank_username,"[a-zA-Z\\s]+",R.string.err_msg_for_namein_bank);

        sector.setEnabled(false);
        employer.setEnabled(false);

        Bankdetails();
        Statedetails();
        Sectorlist();
        banks = new String[]{"Select the Bank"};
        states=new String[]{"Select the State"};
        districts=new String[]{"Select the District"};
        sectors=new String[]{"Select the Sector"};
        employers=new String[]{"Select the Employer"};
        Statelist = new ArrayList<>(Arrays.asList(states));
        bankslist = new ArrayList<>(Arrays.asList(banks));
        districtlist=new ArrayList<>(Arrays.asList(districts));
        sectorlist=new ArrayList<>(Arrays.asList(sectors));
        employerlist=new ArrayList<>(Arrays.asList(employers));

        input_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                 if(yearobirth.equals("Year")){
                    Toast.makeText(getApplicationContext(),"Year must be selected",Toast.LENGTH_LONG).show();

                }
               else if (gender.equals("Select Gender")){
                    Toast.makeText(getApplicationContext(),"Gender must be selected",Toast.LENGTH_LONG).show();
                }

               else if (state1.equals("Select the State")){
                    Toast.makeText(getApplicationContext(),"State must be selected",Toast.LENGTH_LONG).show();
                }

             else if (district1.equals("Select the District")){
                    Toast.makeText(getApplicationContext(),"District must be selected",Toast.LENGTH_LONG).show();
                }

               else if (eduction1.equals("Select Education")){
                    Toast.makeText(getApplicationContext(),"Education must be selected",Toast.LENGTH_LONG).show();
                }

               else if (employment1.equals("Are you employed?")){
                    Toast.makeText(getApplicationContext(),"Employment Status must be selected",Toast.LENGTH_LONG).show();
                }

                /*if (employer1.equals("Select the Employer")){
                    Toast.makeText(getApplicationContext(),"Employer must be selected",Toast.LENGTH_LONG).show();
                }*/

               else if (sector1.equals("Select the Sector")){
                    Toast.makeText(getApplicationContext(),"sector must be selected",Toast.LENGTH_LONG).show();
                }

               else if (bankname1.equals("Select the Bank")){
                    Toast.makeText(getApplicationContext(),"Bank  name must be selected",Toast.LENGTH_LONG).show();

                }


                if(awesomeValidation.validate() ) {

                    //Toast.makeText(getApplicationContext(), "data", Toast.LENGTH_LONG).show();
                    Intent ii = new Intent(MainActivity.this, Reverify.class);
                    ii.putExtra("first_namee", input_name.getText().toString());
                    ii.putExtra("last_namee", input_last_name.getText().toString());
                    ii.putExtra("mobile", input_mobile_no.getText().toString());
                    ii.putExtra("aadhar", input_aadhar.getText().toString());
                    ii.putExtra("bankaccount", input_bank_ac.getText().toString());
                    ii.putExtra("doy", yearobirth);
                    ii.putExtra("dom",monthobirth);
                    ii.putExtra("dod",dateobirth);
                    ii.putExtra("gender", gender);
                    ii.putExtra("bank", bankname1);
                    ii.putExtra("state", stateiddd);
                    ii.putExtra("district", districtiddd);
                    ii.putExtra("education", eduction1);
                    ii.putExtra("employed", employment1);
                    ii.putExtra("employer", employeridd);
                    ii.putExtra("sector", sectoridd);
                    ii.putExtra("addline1", input_address1.getText().toString());
                    ii.putExtra("addline2", input_address2.getText().toString());
                    ii.putExtra("pincode", input_pincode.getText().toString());
                    ii.putExtra("nameasinbank", input_bank_username.getText().toString());
                    ii.putExtra("ifsccode", input_ifsc_code.getText().toString());
                     ii.putExtra("pic",encodedphoto);
                    startActivity(ii);

                }else
                {
                    Toast.makeText(getApplicationContext(), "Please fill correct Data", Toast.LENGTH_LONG).show();
                }

            }
        });
        input_photograph.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }

            }
        });
        //Gender
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.gender));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        myspinner.setAdapter(myAdapter);

        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                gender=myspinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });


        //Year of birth

        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Year));
        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        yearofbirth.setAdapter(myAdapter1);

        yearofbirth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                yearobirth=yearofbirth.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });

        //Month of birth

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Month));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        monthofbirth.setAdapter(myAdapter2);

        monthofbirth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                      monthobirth=monthofbirth.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });

        //Date of birth

        ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Date));
        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dateofbirth.setAdapter(myAdapter3);

        dateofbirth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                dateobirth=dateofbirth.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });

        //Education
        ArrayAdapter<String> myAdapter4 = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Education));
        myAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        education.setAdapter(myAdapter4);

        education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                eduction1=education.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });

        //Employment

        ArrayAdapter<String> myAdapterEmployment = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Employment));
        myAdapterEmployment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        employment.setAdapter(myAdapterEmployment);

        employment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                employment1=employment.getSelectedItem().toString();

                if (employment1.equals("No")){
                    employer.setEnabled(false);
                    sector.setEnabled(false);
                }
                else if (employment1.equals("Yes")){
                    employer.setEnabled(true);
                    sector.setEnabled(true);
                }
                employeridd=employerdetail.get(employment1);
                //Toast.makeText(getApplicationContext(),"emp"+employeridd,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });



        //Sector

        ArrayAdapter<String> myAdaptersector = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,sectorlist);
        myAdaptersector.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sector.setAdapter(myAdaptersector);

        sector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                sector1=sector.getSelectedItem().toString();
               String selectedsectortext  = (String) parent.getItemAtPosition(position);

                if(position > 0){

                    sectoridd=sectordetail.get(selectedsectortext);
                   // Toast.makeText(getApplicationContext(), "Selected : " + sectoridd, Toast.LENGTH_SHORT).show();
                    Employerlist(sectoridd);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });

        //Employer


        ArrayAdapter<String> myAdapterEmployer = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,employerlist);
        myAdapterEmployer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employer.setAdapter(myAdapterEmployer);

        employer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                employer1=employer.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });

        //Bankname

        ArrayAdapter<String> myAdapterBankname = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,bankslist);
        myAdapterBankname.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bankname.setAdapter(myAdapterBankname);

        bankname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                bankname1=bankname.getSelectedItem().toString();
                if(position > 0){
                    bankiddd= bankdetail.get(bankname1);
                    //Toast.makeText(getApplicationContext(),"emp"+bankiddd,Toast.LENGTH_LONG).show();


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });

        //state

        ArrayAdapter<String> myAdapterState = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,Statelist);
        myAdapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        state.setAdapter(myAdapterState);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                state1=state.getSelectedItem().toString();
               selectedstatetext  = (String) parent.getItemAtPosition(position);

                if(position > 0){
                    String value= Statedetail.get(selectedstatetext);
                    stateiddd=value;
                    //Toast.makeText(getApplicationContext(), "Selected : " + stateiddd, Toast.LENGTH_SHORT).show();
                    DistrictDetails(value);

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });


        //District
        ArrayAdapter<String> myAdapterDistrict = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,districtlist);
        myAdapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        district.setAdapter(myAdapterDistrict);

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                district1=district.getSelectedItem().toString();
                districtiddd=districtdetail.get(district1);
                //Toast.makeText(getApplicationContext(),"emp"+districtiddd,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });

    }



    //API For Bank
    private void Bankdetails() {


        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_bank.php";
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jobj = new JSONObject(response);

                    String status= jobj.getString("status");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("bank");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            bankid = c.getString("id");
                            bankvalue = c.getString("name");
                            bankdetail.put(bankvalue, bankid);
                            bankslist.add(bankvalue);

                        }
                       // Toast.makeText(getApplicationContext(),"Success"+bankslist,Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch Bank Details",Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (pd.isShowing()) {
                    pd.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to fetch Bank Details", Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");

                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


    //State List
    private void Statedetails() {


        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_state.php";
       /* pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);*/
       // pd.show();

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jobj = new JSONObject(response);

                    String status= jobj.getString("status");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("state");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            Stateid = c.getString("id");
                            Statevalue = c.getString("name");
                            Statedetail.put(Statevalue, Stateid);
                            Statelist.add(Statevalue);
                        }
                        //Toast.makeText(getApplicationContext(),"Success"+bankslist,Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch States",Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
               /* if (pd.isShowing()) {
                    pd.dismiss();
                }*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to fetch State list", Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");

                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    //District_List
    private void DistrictDetails(final String districtidd) {


        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_district.php";
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
         pd.show();

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Success"+districtlist,Toast.LENGTH_LONG).show();
                try {
                    JSONObject jobj = new JSONObject(response);
districtlist.clear();
                    String status= jobj.getString("status");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("district");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            districtid = c.getString("id");
                            districtvalue = c.getString("name");
                            districtdetail.put(districtvalue, districtid);
                                districtlist.add(districtvalue);


                        }
                        //Toast.makeText(getApplicationContext(),"Success"+districtlist,Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch Districts",Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (pd.isShowing()) {
                    pd.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                   pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to fetch Districts", Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");
                map.put("state_id",districtidd);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    //Sector_List
    private void Sectorlist() {


        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_sector.php";

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);

                    String status= jobj.getString("status");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("sector");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            sectorid = c.getString("id");
                            sectorvalue = c.getString("name");
                            sectordetail.put(sectorvalue, sectorid);
                            sectorlist.add(sectorvalue);

                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch Sectors",Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (pd.isShowing()) {
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");

                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    //Employer_list
    private void Employerlist(final String Sectorvalue) {
        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_employer.php";
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                employerlist.clear();
                try {
                    JSONObject jobj = new JSONObject(response);

                    String status= jobj.getString("status");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("employer");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            employerid = c.getString("id");
                            employervalue = c.getString("name");
                            employerdetail.put(employervalue, employerid);
                            employerlist.add(employervalue);

                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch Employer Details",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (pd.isShowing()) {
                    pd.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to fetch Employer List", Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> map = new HashMap<>();
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/x-www-form-urlencoded");
                map.put("sector_id",Sectorvalue);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                //Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            int currentBitmapWidth = photo.getWidth();
            int currentBitmapHeight = photo.getHeight();
            int newHeight = (int) Math.floor((double) currentBitmapHeight *( (double) currentBitmapWidth / (double) currentBitmapWidth));
            Bitmap newbitMap = Bitmap.createScaledBitmap(photo, currentBitmapWidth, newHeight, true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            newbitMap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encodedphoto = Base64.encodeToString(byteArray, Base64.DEFAULT);

        }
    }

}
