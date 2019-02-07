package com.radiant.rpl.testa;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ebanx.swipebtn.OnActiveListener;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;


import radiant.rpl.radiantrpl.R;

public class Welcome_page extends AppCompatActivity {
    SessionManager session;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    TextView textView2,logoutt;
    TextView ttv;
    boolean active;
    @RequiresApi(api = Build.VERSION_CODES.M)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        blink();
        session = new SessionManager();
        textView2=findViewById(R.id.textView2);
        ttv=findViewById(R.id.textView);
        logoutt=findViewById(R.id.logouttext);
        SwipeButton enableButton = (SwipeButton) findViewById(R.id.swipe_btn);
        enableButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
            }
        });
        enableButton.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {
                Intent ii=new Intent(Welcome_page.this,StudenAtten.class);
                startActivity(ii);

            }
        });

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains("Name")) {
            textView2.setText(sharedpreferences.getString("Name", ""));
        }


        //logout Code
        logoutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setPreferences(Welcome_page.this, "status", "0");
                Intent in = new Intent(Welcome_page.this, SignInAct.class);
                startActivity(in);
            }
        });
    }

    private void blink() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 1000;    //in milissegunds
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView txt = (TextView) findViewById(R.id.textView2);
                        if (txt.getVisibility() == View.VISIBLE) {
                            txt.setVisibility(View.INVISIBLE);
                        } else {
                            txt.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}



