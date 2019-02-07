package com.radiant.rpl.testa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import radiant.rpl.radiantrpl.R;

public class BatchSelection extends AppCompatActivity {

    private Button bb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_selection);

        bb = findViewById(R.id.batchselectproceed);

        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ii = new Intent(BatchSelection.this, Testinstruction.class);
                //ii.putExtra("batchid", Batchid);
                startActivity(ii);

            }
        });




    }
}
