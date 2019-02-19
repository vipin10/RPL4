package com.radiant.rpl.testa.ExamSection;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.radiant.rpl.testa.LocalDB.DbAutoSave;
import com.radiant.rpl.testa.MyNetwork;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import radiant.rpl.radiantrpl.R;

public class TestQuestion extends AppCompatActivity {
    FragmentParent fragmentParent;
    TextView textView,finalSubmitbutton,reviewlaterr;
    Cursor cursor,cursor11;
    Toolbar t1;
    LinearLayout len;
    ImageButton imgRight;
    GridView drawer_Right;
    DrawerLayout mdrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    Context con=this;
    CustomAdapter cl1,cl2;
    String name[];
    String j;
    ArrayList<String> studentidlist;
    ArrayList<String> questioniddd;
    ArrayList<String> answeredoptionn;
    ArrayList<String> statusoption;
    ProgressDialog pdd;
    String aaa,bbb,ccc;
    DbAutoSave dbAutoSave;
    SQLiteDatabase mDatabase;
    ArrayList<SetterGetter> employeeList;
    ArrayList<String> aa=new ArrayList<>();
    ArrayList<String> bb=new ArrayList<>();
    ArrayList<String> queid=new ArrayList<>();
    ArrayList<String[]> options=new ArrayList<>();
    ArrayList<String> options1=new ArrayList<>();
    ArrayList<String> options2=new ArrayList<>();
    ArrayList<String> options3=new ArrayList<>();
    ArrayList<String> options4=new ArrayList<>();
    ArrayList<String> statuss=new ArrayList<>();

    SetterGetter setterGetter;
    String[] title = {
            "New Delhi",
            "Mumbai",
            "Bangalore",
            "Ahmedabad",
    };
    String[] title1 = {
            "Narendra Modi",
            "Jawahar Lal Nehru",
            "Karamchand Ghandhi",
            "Anil Kapoor",
    };
    String[] title2 = {
            "Shiela Dixit",
            "Arvind Kejriwal",
            "Manish Shishodia",
            "Rajat Sharma",
    };
    String[] title3 = {
            "Imraan Khan",
            "Kapil Dev",
            "Mahendra Singh Dhoni",
            "Ravindra Jadeja",
    };
    String[] title4 = {
            "Ved Vyas",
            "TulsiDas",
            "Ramanand sagar",
            "Vishwamitra",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_question);
        getIDs();
        t1=findViewById(R.id.toolbar);
        setSupportActionBar(t1);
        studentidlist=new ArrayList<>();
        questioniddd=new ArrayList<>();
        answeredoptionn =new ArrayList<>();
        options.add(title);
        options.add(title1);
        options.add(title2);
        options.add(title3);
        options.add(title4);
        employeeList=new ArrayList<>();
        dbAutoSave = new DbAutoSave(getApplicationContext());
        mDatabase= openOrCreateDatabase(DbAutoSave.DATABASE_NAME, MODE_PRIVATE, null);
        Questionlist();
        setterGetter =new SetterGetter();


        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mdrawerLayout.isDrawerOpen(len)){
                    mdrawerLayout.closeDrawer(len);
                    getData();
                    if (statuss.size()>0){
                        statuss.clear();
                        getStatusdata();
                    }else {
                        getStatusdata();
                    }
                }
                else if (!mdrawerLayout.isDrawerOpen(len)){
                    mdrawerLayout.openDrawer(len);
                    getData();
                    if (statuss.size()>0){
                        statuss.clear();
                        getStatusdata();
                    }else {
                        getStatusdata();
                    }

                }
            }
        });
    }


    private void getIDs() {
        fragmentParent = (FragmentParent) this.getSupportFragmentManager().findFragmentById(R.id.fragmentParent);
        View vv=findViewById(R.id.count_down_strip);
        reviewlaterr=vv.findViewById(R.id.mark);
        textView=vv.findViewById(R.id.timer);
        finalSubmitbutton=vv.findViewById(R.id.finish);
        drawer_Right=findViewById(R.id.drawer_right);
        imgRight=findViewById(R.id.imgRight);
        len=findViewById(R.id.len1);
        mdrawerLayout=findViewById(R.id.activity_main1);
        mdrawerLayout.addDrawerListener(mDrawerToggle);

    }

    @Override
    protected void onStart() {
        super.onStart();
        finalSubmitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertbox = new AlertDialog.Builder(v.getContext())
                        .setMessage("Click Yes to schedule Test for Final Submission.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                getalldata();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        })
                        .show();
              /*  Intent ii=new Intent(TestQuestion.this, TestQuestion1.class);
                startActivity(ii);*/
            }
        });

       /* reviewlaterr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/
    }


    private void Questionlist() {
        pdd = new ProgressDialog(TestQuestion.this);
        pdd.setMessage("Loading...");
        pdd.show();
        String serverURL = "https://www.skillassessment.org/ssc/android_connect/batch_questions.php";


        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    //Toast.makeText(getApplicationContext(),"Details are"+response,Toast.LENGTH_LONG).show();
                    String status= jobj.getString("status");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("questions");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            aa.add(c.getString("question_id"));
                            bb.add(c.getString("question"));
                            queid.add(c.getString("question_id"));
                            options1.add(c.getString("option1"));
                            options2.add(c.getString("option2"));
                            options3.add(c.getString("option3"));
                            options4.add(c.getString("option4"));

                        }

                        for (int ii=0;ii<=aa.size()-1;ii++) {
                            fragmentParent.addPage(aa.get(ii) + "",bb.get(ii),options1.get(ii),options2.get(ii),options3.get(ii),options4.get(ii));

                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (pdd.isShowing()) {
                    pdd.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (pdd.isShowing()) {
                    pdd.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Error: Please try again Later", Toast.LENGTH_LONG).show();
            }
        }) {
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
                map.put("batch_id", "RASCI");
                map.put("language","en");

                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    CountDownTimer tt= new CountDownTimer(300000, 1000) {

        public void onTick(long millisUntilFinished) {

            textView.setText( String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));  ;

        }

        public void onFinish() {
            textView.setText("done!");
        }

    }.start();

    public void getalldata(){
        cursor=dbAutoSave.getData("1");
        ArrayList<SetterGetter> dataList = new ArrayList<SetterGetter>();
        if (cursor != null) {
            cursor.moveToFirst();

            do {
                SetterGetter data = new SetterGetter();
                data.student_id = cursor.getString(1);
                data.que_id = cursor.getString(2);
                data.selected_answer = cursor.getString(3);

                questioniddd.add(bbb);
                answeredoptionn.add(ccc);
                dataList.add(data);

            } while (cursor.moveToNext());
            Datalist listOfData = new Datalist();
            listOfData.dataList = dataList;

            Gson gson = new Gson();
            String jsonInString = gson.toJson(listOfData); // Here you go!
            System.out.println("aasddd"+jsonInString);
            cursor.close();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

      public void getStatusdata(){
        cursor11=dbAutoSave.getData1("1");
          if (cursor11 != null) {
              cursor11.moveToFirst();

              do {
                  aaa = cursor11.getString(3);
                  // Add into the ArrayList here

                  statuss.add(aaa);

                  System.out.println("aaaabbb"+statuss);
              } while (cursor11.moveToNext());

              cursor11.close();

          }
      }

    public void getData(){
            cl1 = new CustomAdapter(aa, con, statuss);
            cl2 = new CustomAdapter(aa, con, statuss);
            drawer_Right.setAdapter(cl1);

    }


}
