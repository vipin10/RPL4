package com.radiant.rpl.testa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import radiant.rpl.radiantrpl.R;

public class Start_Registration extends AppCompatActivity {

    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__registration);

        b1=findViewById(R.id.buttonregister);
        b2=findViewById(R.id.login);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ii=new Intent(Start_Registration.this,MainActivity.class);
               startActivity(ii);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ii=new Intent(Start_Registration.this,SignInAct.class);
                startActivity(ii);
            }
      });









    }
}
