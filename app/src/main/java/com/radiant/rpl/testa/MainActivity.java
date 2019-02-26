package com.radiant.rpl.testa;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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

    Spinner yearofbirth,monthofbirth,dateofbirth,education,employment,employer,sector,bankname,state,district,input_jobrole,input_layout_prefferedlanguage,category;
    EditText input_name,input_last_name,input_mobile_no,input_address1
            ,input_address2,input_pincode,input_aadhar,input_bank_ac,input_ifsc_code,input_bank_username,input_empid,input_loc;
ProgressDialog pd;
    String[] banks,states,districts,employers,jobrole;
    List<String> bankslist,Statelist,districtlist,sectorlist,employerlist,jobrolelist,preflang;
    HashMap<String, String> bankdetail = new HashMap<>();
    HashMap<String, String> Jobrolelist = new HashMap<>();
    HashMap<String,String> Statedetail=new HashMap<>();
    HashMap<String,String> districtdetail=new HashMap<>();
    HashMap<String,String> sectordetail=new HashMap<>();
    HashMap<String,String> employerdetail =new HashMap<>();
    HashMap<String,String> employdetail =new HashMap<>();
    HashMap<String,String> langdetail =new HashMap<>();
    Button input_submit;
    CheckBox checkBox;
    String[] sectors=new String[]{"Select the Sector"};
    String[] preflangg=new String[]{"Select the Preffered Language"};
    ImageView input_photograph,input_aadharpic;
    String Stateid,Statevalue,bankid,bankvalue,districtid,districtvalue,selectedstatetext,sectorid,sectorvalue,employerid,employervalue,jobroleid,jobrolevalue,preflangid,preflangvalue;
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_AADHAR_REQUEST = 1889;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String yearobirth,monthobirth,dateobirth;
    AwesomeValidation awesomeValidation;
    String gender,eduction1,employment1,employer1,sector1,bankname1,state1,district1,encodedphoto,encodedphotoaadhar,jobrole1,preflang1,categoryy;
    String bankiddd,stateiddd,districtiddd,employeridd,employeridname,sectoridd,jobroleeiddd,preflangiddd;
    NetworkStateReceiver networkStateReceiver;
    SwipeRefreshLayout mySwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner myspinner = findViewById(R.id.input_layout_gender);
        yearofbirth=findViewById(R.id.input_layout_year);
        category=findViewById(R.id.input_layout_category);
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
        input_aadharpic=findViewById(R.id.input_photograph_aadhar);
        input_submit=findViewById(R.id.btn_signup);
        input_name=findViewById(R.id.input_name);
        input_last_name=findViewById(R.id.input_last_name);
        input_mobile_no=findViewById(R.id.input_mobile_no);
        input_address1=findViewById(R.id.input_address1);
        input_address2=findViewById(R.id.input_address2);
        input_pincode=findViewById(R.id.input_pincode);
        input_jobrole=findViewById(R.id.input_layout_jobrole);
        input_empid=findViewById(R.id.input_empid);
        input_loc=findViewById(R.id.input_loc);
        input_aadhar=findViewById(R.id.input_aadhar);
        input_bank_ac=findViewById(R.id.input_bank_ac);
        input_ifsc_code=findViewById(R.id.input_ifsc_code);
        input_bank_username = findViewById(R.id.input_bank_username);
        input_layout_prefferedlanguage=findViewById(R.id.input_layout_prefferedlanguage);
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        checkBox = findViewById(R.id.checkBox);





        awesomeValidation.addValidation(MainActivity.this, R.id.input_name,"[a-zA-Z\\s]+", R.string.err_msg_for_first_name);
        awesomeValidation.addValidation(MainActivity.this, R.id.input_last_name,"[a-zA-Z\\s]+", R.string.err_msg_for_last_name);
        awesomeValidation.addValidation(MainActivity.this, R.id.input_address1,"(.|\\s)*\\S(.|\\s)*", R.string.err_msg_for_address1);
        awesomeValidation.addValidation(MainActivity.this, R.id.input_pincode,"^[0-9]{6}$", R.string.err_msg_pincode);
        awesomeValidation.addValidation(MainActivity.this, R.id.input_bank_ac,"^[0-9]{11,16}$", R.string.err_msg_for_acno);
        awesomeValidation.addValidation(MainActivity.this, R.id.input_ifsc_code,"^[A-Z0-9]{6,12}$", R.string.err_msg_for_ifsc);
        awesomeValidation.addValidation(MainActivity.this, R.id.input_aadhar,"^[0-9]{12}$", R.string.err_msg_foraadhar);
        awesomeValidation.addValidation(MainActivity.this, R.id.input_mobile_no,"^[0-9]{10}$", R.string.err_msg_formobile);
        awesomeValidation.addValidation(MainActivity.this, R.id.input_bank_username,"[a-zA-Z\\s]+", R.string.err_msg_for_namein_bank);
        sector.setEnabled(false);
        employer.setEnabled(false);
        input_jobrole.setEnabled(false);
        input_empid.setEnabled(false);
        input_loc.setEnabled(false);
        input_layout_prefferedlanguage.setEnabled(false);
        Bankdetails();
        Statedetails();
        Employerlist();
        banks = new String[]{"Select the Bank"};
        states=new String[]{"Select the State"};
        districts=new String[]{"Select the District"};
        employers=new String[]{"Select the Employer"};
        jobrole=new String[]{"Select the Jobrole"};
        Statelist = new ArrayList<>(Arrays.asList(states));
        bankslist = new ArrayList<>(Arrays.asList(banks));
        districtlist=new ArrayList<>(Arrays.asList(districts));
        sectorlist=new ArrayList<>(Arrays.asList(sectors));
        employerlist=new ArrayList<>(Arrays.asList(employers));
        jobrolelist=new ArrayList<>(Arrays.asList(jobrole));
        preflang=new ArrayList<>(Arrays.asList(preflangg));
        mySwipeRefreshLayout=new SwipeRefreshLayout(getApplicationContext());
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

               else if (sector1.equals("Select the Sector")){
                    Toast.makeText(getApplicationContext(),"sector must be selected",Toast.LENGTH_LONG).show();
                }

               else if (bankname1.equals("Select the Bank")){
                    Toast.makeText(getApplicationContext(),"Bank  name must be selected",Toast.LENGTH_LONG).show();

                }


                else if(awesomeValidation.validate() && !(gender.equals("Select Gender"))&& !state1.equals("Select the State") && !yearobirth.equals("Year")
                         && !district1.equals("Select the District") && !eduction1.equals("Select Education") && !employment1.equals("Are you employed?")
                         && !(sector1.equals("Select the Sector")) && !(preflang1.equals("Select the Preffered Language")) && !(bankname1.equals("Select the Bank")) && checkBox.isChecked() && encodedphoto!=null) {

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
                    ii.putExtra("bank", bankiddd);
                    ii.putExtra("state", stateiddd);
                    ii.putExtra("district", districtiddd);
                    ii.putExtra("education", eduction1);
                    ii.putExtra("employed", employment1);
                    ii.putExtra("employer", employeridname);
                    ii.putExtra("sector", sectoridd);
                    ii.putExtra("addline1", input_address1.getText().toString());
                    ii.putExtra("addline2", input_address2.getText().toString());
                    ii.putExtra("pincode", input_pincode.getText().toString());
                    ii.putExtra("nameasinbank", input_bank_username.getText().toString());
                    ii.putExtra("ifsccode", input_ifsc_code.getText().toString());
                    ii.putExtra("jobrole",jobroleeiddd);
                    ii.putExtra("empid",input_empid.getText().toString());
                    ii.putExtra("location",input_loc.getText().toString());
                    ii.putExtra("preflang",preflangiddd);
                     ii.putExtra("pic",encodedphoto);
                     ii.putExtra("picaadhar",encodedphotoaadhar);
                    startActivity(ii);

                }else
                {
                    Toast.makeText(getApplicationContext(), "The form is not filled correctly.Please verify it and submit.", Toast.LENGTH_LONG).show();
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
        input_aadharpic.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_AADHAR_REQUEST);
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
                    sector.setEnabled(false);
                    employer.setEnabled(false);
                    input_jobrole.setEnabled(false);
                    input_empid.setEnabled(false);
                    input_loc.setEnabled(false);
                }
                else if (employment1.equals("Yes")){
                    employer.setEnabled(true);
                    //sector.setEnabled(true);
                    input_jobrole.setEnabled(true);
                    input_empid.setEnabled(true);
                    input_loc.setEnabled(true);
                    input_layout_prefferedlanguage.setEnabled(true);
                }
                employeridd=employerdetail.get(employment1);
                //Toast.makeText(getApplicationContext(),"emp"+employeridd,Toast.LENGTH_LONG).show();

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
                if(position > 0) {
                    employer1 = employer.getSelectedItem().toString();
                    employeridname = employdetail.get(employer1);
                    Sectorlist(employeridname);
                    languageSelect(employeridname);
                    getJobroleList(employeridname);

                }
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

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //jobrole

        ArrayAdapter<String> jobroleadapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,jobrolelist);
        jobroleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        input_jobrole.setAdapter(jobroleadapter);

        input_jobrole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                if(position > 0) {
                    jobrole1 = input_jobrole.getSelectedItem().toString();
                    jobroleeiddd=Jobrolelist.get(jobrole1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });

        //Choose category
        ArrayAdapter<String> categoryadapt = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,jobrolelist);
        categoryadapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        category.setAdapter(jobroleadapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                if(position > 0) {
                    categoryy = category.getSelectedItem().toString();
                    //jobroleeiddd=Jobrolelist.get(jobrole1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }


        });


        //Preffered Exam Language
        ArrayAdapter<String> preflanguage = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,preflang);
        preflanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        input_layout_prefferedlanguage.setAdapter(preflanguage);

        input_layout_prefferedlanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                if(position > 0) {
                    preflang1 = input_layout_prefferedlanguage.getSelectedItem().toString();
                    preflangiddd=langdetail.get(preflang1);
                }
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




    @Override
    protected void onResume() {
        super.onResume();
         networkStateReceiver= new NetworkStateReceiver(new NetworkStateReceiver.NetworkListener() {
            @Override
            public void onNetworkAvailable() {
                input_submit.setEnabled(true);
            }

            @Override
            public void onNetworkUnavailable() {
                input_submit.setEnabled(false);
                Toast.makeText(getApplicationContext(),"Internet Not available",Toast.LENGTH_LONG).show();
            }
        });
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
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

    //Language Api Call
    private void languageSelect(final String cmp_id) {


        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_language.php";

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jobj = new JSONObject(response);

                    String status= jobj.getString("status");

                    if (status.equals("1")){
                        if (preflang.size()>2){
                            preflang.clear();
                        }
                        preflang.add("Choose the Prefferred Language");
                        JSONArray jsonArray=jobj.getJSONArray("language");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            preflangid = c.getString("language_code");
                            preflangvalue = c.getString("name");
                            langdetail.put(preflangvalue,preflangid );
                            preflang.add(preflangvalue);
                        }

                        // Toast.makeText(getApplicationContext(),"Success"+bankslist,Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch Language Details",Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed to fetch Language Details", Toast.LENGTH_LONG).show();
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
                map.put("company_id",cmp_id);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


    //State List
    private void Statedetails() {


        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_state.php";


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
                    String status= jobj.getString("status");
                    if (districtlist.size()>2){
                        districtlist.clear();
                    }
                    districtlist.add("Choose the District");
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

    //Employer_List
    private void Employerlist() {
       /* pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
*/
        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_employer.php";

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);

                    String status= jobj.getString("status");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("employer");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            employerid = c.getString("id");
                            employervalue = c.getString("name");
                            employdetail.put(employervalue, employerid);
                            employerlist.add(employervalue);
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch Employers",Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
               /* if (pd.isShowing()) {
                }*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //pd.dismiss();
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

    //Sector_list
    private void Sectorlist(final String Sectorvalue) {
        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_sector.php";


        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jobj = new JSONObject(response);
                    String status= jobj.getString("status");
                    if (sectorlist.size()>2){
                    sectorlist.clear();
                    }
                    sectorlist.add("Choose the Sector");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("sector");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            sectorid = c.getString("id");
                            sectorvalue = c.getString("name");
                            sectordetail.put(sectorvalue, sectorid);
                            sectorlist.add(sectorvalue);
                            sector.setSelection(sectorlist.size()-1);

                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch Sector Details",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Sectors List", Toast.LENGTH_LONG).show();
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
                map.put("company_id",Sectorvalue);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    //Jobrole Api Call
    private void getJobroleList(final String sscid) {
        String serverURL = "https://www.skillassessment.org/sdms/android_connect/get_jobrole.php";


        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jobj = new JSONObject(response);
                    String status= jobj.getString("status");
                    //jobrolelist.clear();
                    if (jobrolelist.size()>2){
                        jobrolelist.clear();
                    }
                    jobrolelist.add("Choose the Job Role");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("jobrole");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            jobroleid = c.getString("jobrole_id");
                            jobrolevalue = c.getString("name");
                            Jobrolelist.put(jobrolevalue, jobroleid);
                            jobrolelist.add(jobrolevalue);
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed to fetch Job Roles",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Failed to fetch Job Roles", Toast.LENGTH_LONG).show();
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
                map.put("company_id",sscid);
                System.out.println("aaaaaa"+map);
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
            }

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            int currentBitmapWidth = photo.getWidth();
            int currentBitmapHeight = photo.getHeight();
            input_photograph.setImageBitmap(photo);
            int newHeight = (int) Math.floor((double) currentBitmapHeight *( (double) currentBitmapWidth / (double) currentBitmapWidth));
            Bitmap newbitMap = Bitmap.createScaledBitmap(photo, currentBitmapWidth, newHeight, true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            newbitMap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encodedphoto = Base64.encodeToString(byteArray, Base64.DEFAULT);

        }

        if (requestCode == CAMERA_AADHAR_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            int currentBitmapWidth = photo.getWidth();
            int currentBitmapHeight = photo.getHeight();
            mySwipeRefreshLayout.setRefreshing(false);
            input_aadharpic.setImageBitmap(photo);
            int newHeight = (int) Math.floor((double) currentBitmapHeight *( (double) currentBitmapWidth / (double) currentBitmapWidth));
            Bitmap newbitMap = Bitmap.createScaledBitmap(photo, currentBitmapWidth, newHeight, true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            newbitMap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encodedphotoaadhar = Base64.encodeToString(byteArray, Base64.DEFAULT);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkStateReceiver);
    }
}
