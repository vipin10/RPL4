package com.radiant.rpl.testa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.radiant.rpl.testa.ExamSection.TestQuestion;

import radiant.rpl.radiantrpl.R;

public class Testinstruction extends AppCompatActivity {
    Button testinstructproceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testinstruction);
        testinstructproceed=findViewById(R.id.testinstructproceed);
        testinstructproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(Testinstruction.this, TestQuestion.class);
                startActivity(ii);
            }
        });
    }
}
