package com.radiant.rpl.testa;


import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import dmax.dialog.SpotsDialog;
import radiant.rpl.radiantrpl.R;

public class SignInAct extends AppCompatActivity {
    private ImageView bookIconImageView;
    private TextView bookITextView,skiptextview;
    private ProgressBar loadingProgressBar;
    private RelativeLayout rootView, afterAnimationView;
    Button loginsubmit;
    TextInputEditText username,passowrd;
    String uname,pass;
    SessionManager sessionManager;
    SharedPreferences sharedpreferences;
    final String mypreference = "mypref";
     String name,batchid,mobile,exam_status,address;
    private android.app.AlertDialog progressDialog;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);

        progressDialog = new SpotsDialog(SignInAct.this, R.style.Custom);
        sessionManager = new SessionManager();
        sharedpreferences=getSharedPreferences(mypreference,Context.MODE_PRIVATE);
        initViews();
        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                bookITextView.setVisibility(GONE);
                loadingProgressBar.setVisibility(GONE);
                rootView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSplashText));
                //bookIconImageView.setImageResource(R.drawable.background_color_book);
                startAnimation();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        loginsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().toString().equals(null)){

                    Toast.makeText(getApplicationContext(),"The required fields Username and password can't be empty",Toast.LENGTH_LONG).show();
                }else {
                    sendDataServer();
                }
            }
        });
        skiptextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(SignInAct.this,MainActivity.class);
                startActivity(ii);
            }
        });
    }

    private void initViews() {
        bookIconImageView = findViewById(R.id.bookIconImageView);
        bookITextView = findViewById(R.id.bookITextView);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
        rootView = findViewById(R.id.rootView);
        afterAnimationView = findViewById(R.id.afterAnimationView);
        username=findViewById(R.id.emailEditText);
        passowrd=findViewById(R.id.passwordEditText);
        loginsubmit=findViewById(R.id.loginButton);
        skiptextview=findViewById(R.id.skipTextView);
    }

    private void startAnimation() {
        ViewPropertyAnimator viewPropertyAnimator = bookIconImageView.animate();
        viewPropertyAnimator.x(50f);
        viewPropertyAnimator.y(100f);
        viewPropertyAnimator.setDuration(1000);
        viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                afterAnimationView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void sendDataServer() {

        progressDialog.show();
        String serverURL = "https://www.skillassessment.org/sdms/android_connect/login.php";
        uname=username.getText().toString();
        pass= passowrd.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobj = new JSONObject(response);
                    status= jobj.getString("status");
                    exam_status=jobj.getString("exam_status");

                    if (status.equals("1")) {
                    if (exam_status.equals("Not Attempted")){
                        JSONObject jsonObject = jobj.getJSONObject("student_details");
                        for (int i = 0; i < jsonObject.length(); i++) {
                            name = jsonObject.getString("firstname");
                            batchid = jsonObject.getString("batch_id");
                            mobile=jsonObject.getString("mobile");
                            address=jsonObject.getString("address1");
                            sessionManager.setPreferences(getApplicationContext(), "status", "1");
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("Name", name);
                            editor.putString("address", address);
                            editor.putString("batchid", batchid);
                            editor.putString("assessorid",mobile);
                            editor.apply();
                            Intent ii=new Intent(SignInAct.this, Welcome_page.class);
                            startActivity(ii);
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"You have already attempted the exam.",Toast.LENGTH_LONG).show();
                    }

                    }

                    else if (status.equals("0")){
                        Toast.makeText(getApplicationContext(),"Wrong Credentials.",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Unable to Login",Toast.LENGTH_LONG).show();
                    }
                }

                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: Please try again Later", Toast.LENGTH_LONG).show();
                }

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
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
                map.put("user_name", uname);
                map.put("password", pass);
                map.put("app_version", "1.0");
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyNetwork.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }




}
