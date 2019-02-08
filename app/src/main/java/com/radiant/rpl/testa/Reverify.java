package com.radiant.rpl.testa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import radiant.rpl.radiantrpl.R;

public class Reverify extends AppCompatActivity {

    EditText fname_txt,lname_txt,mob_txt,aadharno_txt,bankacc_txt;
    String fname,lname,mob,aadharno,bankacc,yearobirth,monthobirth,dateobirthh,gender,bank1,statee,districtt,educationn,employedd,employerr,sectorr,addline11,addline22,pincode1,nameasinbank1,
    iffccode1,photouri;
    String getFname,getLname,getMob,getAadharno,getBankacc;
    ProgressDialog pd;
    Button btn_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reverify);
        fname_txt=findViewById(R.id.input_fname);
        lname_txt=findViewById(R.id.input_last_lname);
        mob_txt=findViewById(R.id.input_mobile_noo);
        aadharno_txt=findViewById(R.id.input_aadhar_no);
        bankacc_txt=findViewById(R.id.input_bank_acdetails);

        Intent ii=getIntent();
        fname=ii.getStringExtra("first_namee");
        lname=ii.getStringExtra("last_namee");
        mob=ii.getStringExtra("mobile");
        aadharno=ii.getStringExtra("aadhar");
        bankacc=ii.getStringExtra("bankaccount");
        yearobirth=ii.getStringExtra("doy");
        monthobirth=ii.getStringExtra("dom");
        dateobirthh=ii.getStringExtra("dod");
        gender=ii.getStringExtra("gender");
        bank1=ii.getStringExtra("bank");
        statee=ii.getStringExtra("state");
        districtt=ii.getStringExtra("district");
        educationn=ii.getStringExtra("education");
        employedd=ii.getStringExtra("employed");
        employerr=ii.getStringExtra("employer");
        sectorr=ii.getStringExtra("sector");
        addline11=ii.getStringExtra("addline1");
        addline22=ii.getStringExtra("addline2");
        pincode1=ii.getStringExtra("pincode");
        nameasinbank1=ii.getStringExtra("nameasinbank");
        iffccode1=ii.getStringExtra("ifsccode");
        photouri=ii.getStringExtra("pic");

        fname_txt.setText(fname);
        lname_txt.setText(lname);
        mob_txt.setText(mob);
        aadharno_txt.setText(aadharno);
        bankacc_txt.setText(bankacc);



        btn_Register=findViewById(R.id.btn_Register);

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFname=fname_txt.getText().toString();
                getLname=lname_txt.getText().toString();
                getMob=mob_txt.getText().toString();
                getAadharno=aadharno_txt.getText().toString();
                getBankacc=bankacc_txt.getText().toString();
                SaveDetail(getFname,getLname,getMob,getAadharno,getBankacc);


            }
        });
    }


    private void SaveDetail(final String fnamee, final String lnamee, final String mobbb, final String aadhaar, final String bankacccc) {
        String serverURL = "https://www.skillassessment.org/sdms/android_connect/save_student_data.php";
        pd = new ProgressDialog(Reverify.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    System.out.println("sss"+response);
                    String status= jobj.getString("status");
                    if (status.equals("1")){
                          Intent iii=new Intent(Reverify.this,Registration_Done.class);
                          startActivity(iii);


                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Error Saving the details",Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), "error+Error Saving the details", Toast.LENGTH_LONG).show();
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
                map.put("firstname",fnamee);
                map.put("lastname",lnamee);
                map.put("mobile",mobbb);
                map.put("aadhar",aadhaar);
                map.put("bankac",bankacccc);
                map.put("dob",yearobirth+"-"+monthobirth+"-"+dateobirthh);
                map.put("gender",gender);
                map.put("qualification",educationn);
                map.put("employment",employedd);
                map.put("address1",addline11);
                map.put("address2",addline22);
                map.put("state_id",statee);
                map.put("district_id",districtt);
                map.put("pincode",pincode1);
                map.put("aadhar",aadharno);
                map.put("ssc_id",sectorr);
                map.put("sector_id","0");
                map.put("bankname",bank1);
                map.put("name_in_bank",nameasinbank1);
                map.put("ifsc",iffccode1);
                map.put("student_image",photouri);
                System.out.print("ggggggg"+map);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


}
