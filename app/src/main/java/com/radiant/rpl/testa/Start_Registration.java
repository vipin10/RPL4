package com.radiant.rpl.testa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import radiant.rpl.radiantrpl.R;

public class Start_Registration extends AppCompatActivity {
    SessionManager sessionManager;
    Button b1,b2;
    SharedPreferences prefs;
    Long timereset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__registration);
        sessionManager=new SessionManager();
        String stat=sessionManager.getPreferences(getApplicationContext(),"vipin");



        prefs=getSharedPreferences("prefs", MODE_PRIVATE);
        if (stat.equals("1")){

        }
        else if (stat.equals("0")){
            if (prefs.contains("millisLeft")){
                timereset= prefs.getLong("millisLeft", 1);
                prefs.edit().clear().commit();
            }

        }
        b1=findViewById(R.id.buttonregister);
        b2=findViewById(R.id.login);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ii=new Intent(Start_Registration.this,MainActivity.class);
                startActivity(ii);

            }
        }
        );
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ii=new Intent(Start_Registration.this,SignInAct.class);
                startActivity(ii);
            }
        }
        );









    }
}
