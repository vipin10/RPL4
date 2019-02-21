package com.radiant.rpl.testa.ExamSection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
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
import java.util.Locale;
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
    private NotificationHelper mNotificationHelper;


    private static final long START_TIME_IN_MILLIS = 1500000;
    private static final long START_TIME_IN_MILLISR = 00000;
    private android.os.CountDownTimer CountDownTimer;
    private boolean TimerRunning;
    private long TimeLeftInMillis;
    private long EndTime;



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
    ArrayList<String> questatus=new ArrayList<>();

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
        mNotificationHelper = new NotificationHelper(this);




        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mdrawerLayout.isDrawerOpen(len)){
                    mdrawerLayout.closeDrawer(len);
                   // getData();
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
                       // getStatusdata();
                    }

                }
            }
        });


        mdrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }



    private void getIDs() {
        fragmentParent = (FragmentParent) this.getSupportFragmentManager().findFragmentById(R.id.fragmentParent);
        View vv=findViewById(R.id.count_down_strip);
        //reviewlaterr=vv.findViewById(R.id.mark);
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


        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        TimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        TimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateButtons();
        resetTimer();

        if (TimerRunning) {
            EndTime = prefs.getLong("endTime", 0);
            TimeLeftInMillis = EndTime - System.currentTimeMillis();

            if (TimeLeftInMillis < 0) {
                TimeLeftInMillis = 0;
                TimerRunning = false;
                updateCountDownText();
                updateButtons();
            } else {
                startTimer();
            }
        }

        finalSubmitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                resetTimer();
            }
        });


        startTimer();





    }


    private void startTimer() {
        EndTime = System.currentTimeMillis() + TimeLeftInMillis;

        CountDownTimer = new CountDownTimer(TimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

                TimerRunning = false;
                updateButtons();
                resetTimer();
                showDialog();


            }
        }.start();

        TimerRunning = true;
        updateButtons();
    }

    private void resetTimer() {
        TimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        updateButtons();
    }

    private void submitTimer() {
        TimeLeftInMillis = START_TIME_IN_MILLISR;
        updateCountDownText();
        updateButtons();
        TimerRunning = false;
        CountDownTimer.cancel();
    }


    private void updateCountDownText() {
        int minutes = (int) (TimeLeftInMillis / 1000) / 60;
        int seconds = (int) (TimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textView.setText(timeLeftFormatted);
    }

    private void updateButtons() {
        if (TimerRunning) {
        } else {

            if (TimeLeftInMillis < 1000) {
            } else {
            }

            if (TimeLeftInMillis < START_TIME_IN_MILLIS) {

            } else {

            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("millisLeft", TimeLeftInMillis);
        editor.putBoolean("timerRunning", TimerRunning);
        editor.putLong("endTime", EndTime);

        editor.apply();

        if (CountDownTimer != null) {
            CountDownTimer.cancel();
        }

        SendInNotification("Timer is Runing", (TimeLeftInMillis / 1000) / 60, (TimeLeftInMillis / 1000) % 60);


    }








    private void Questionlist() {
        pdd = new ProgressDialog(TestQuestion.this);
        pdd.setMessage("Loading...");
        pdd.show();
        String serverURL = "https://www.skillassessment.org/sdms/android_connect/batch_questions.php";


        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    //Toast.makeText(getApplicationContext(),"Details are"+response,Toast.LENGTH_LONG).show();
                    String status= jobj.getString("status");
                    if (status.equals("1")){
                        JSONArray jsonArray=jobj.getJSONArray("theory_questions");
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
                map.put("batch_id", "148");
                map.put("language","hi");
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


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

                /*questioniddd.add(bbb);
                answeredoptionn.add(ccc);*/
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
                  bbb = cursor11.getString(2);
                  // Add into the ArrayList here

                  statuss.add(aaa);
                  questatus.add(bbb);
                  System.out.println("aaaabbb"+statuss);
              } while (cursor11.moveToNext());

              cursor11.close();

          }
      }

    public void getData(){
            cl1 = new CustomAdapter(aa, con, statuss,questatus);
            cl2 = new CustomAdapter(aa, con, statuss,questatus);
            drawer_Right.setAdapter(cl1);

    }


    public void SendInNotification(String title, long timerNotify, long timerinSec) {

        NotificationCompat.Builder nb = mNotificationHelper.getSendNotification(title, timerNotify, timerinSec);
        mNotificationHelper.getManger().notify(1, nb.build());


    }




    public void showDialog() {


        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("Click Yes to schedule Test for Next Section.")
                .setCancelable(false)
                .setPositiveButton("Yes And proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent ii = new Intent(TestQuestion.this,Testviva.class);
                        startActivity(ii);

                        finish();

                    }
                }).create();


        alertDialog.show();

    }



}
