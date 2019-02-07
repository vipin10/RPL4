package com.radiant.rpl.testa.ExamSection;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.radiant.rpl.testa.LocalDB.DbAutoSave;
import radiant.rpl.radiantrpl.R;

/**
 * Created by DAT on 9/1/2015.
 */
public class FragmentChild extends Fragment implements View.OnClickListener {
    String childname,quename;
    TextView textViewChildName,t1,optiona,optionb,optionc,optiond,titlea,titleb,titlec,titled;
    LinearLayout l1,l2,l3,l4;
    DbAutoSave dbAutoSave;
    String idd,Stuid;
    String query,query1,assessoridd;
    SharedPreferences sharedPreferences,sharedPreferences1;
    public static final String mypreference = "mypref";
    public static final String mypreference1 = "mypref1";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child, container, false);
        Bundle bundle = getArguments();
        childname = bundle.getString("data");
        quename =bundle.getString("daa");
        dbAutoSave = new DbAutoSave(getContext());
        getIDs(view);
        setEvents();
        idd=dbAutoSave.getDataOfSingleClient(query1,query);
        sharedPreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        sharedPreferences1 = getActivity().getSharedPreferences(mypreference1, Context.MODE_PRIVATE);
        Stuid=sharedPreferences1.getString("StuName","");
        System.out.println("aaa"+Stuid);
        assessoridd=sharedPreferences.getString("assessorid","");

        return view;
    }

    private void getIDs(View view) {
        textViewChildName =view.findViewById(R.id.Quesnoo);
        t1=view.findViewById(R.id.que);
        optiona=view.findViewById(R.id.optiona);
        optionb=view.findViewById(R.id.optionb);
        optionc=view.findViewById(R.id.optionc);
        optiond=view.findViewById(R.id.optiond);
        titlea=view.findViewById(R.id.optionnoa);
        titleb=view.findViewById(R.id.optionnob);
        titlec=view.findViewById(R.id.optionnoc);
        titled=view.findViewById(R.id.optionnod);
        l1=view.findViewById(R.id.option1);
        l2=view.findViewById(R.id.option2);
        l3=view.findViewById(R.id.option3);
        l4=view.findViewById(R.id.option4);

        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        textViewChildName.setText(childname+".)");
        t1.setText(quename);


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void changeColorBack(TextView textView)
    {
        textView.setBackground(getResources().getDrawable(R.drawable.rounded_grey));
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            onResume();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }
        query=t1.getText().toString();
        query1=Stuid;
    }

    private void setEvents() {

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        changeColorBack(titlea);
        changeColorBack(titleb);
        changeColorBack(titlec);
        changeColorBack(titled);

        switch (v.getId()) {
            case R.id.option1:
                titlea.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_txt));
                idd=dbAutoSave.getDataOfSingleClient(query1,query);
                if (idd!=(null)){
                    dbAutoSave.updateData(assessoridd,Stuid,t1.getText().toString(),optiona.getText().toString());
                    //Toast.makeText(getContext(),"Updated"+optiona.getText().toString(),Toast.LENGTH_LONG).show();
                }
                else {
                    dbAutoSave.insertData(assessoridd,Stuid,t1.getText().toString(),optiona.getText().toString());
                    // Toast.makeText(getContext(),"inserted"+optiona.getText().toString(),Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.option2:
                titleb.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_txt));
                idd=dbAutoSave.getDataOfSingleClient(query1,query);
                if (idd!=(null)){
                    dbAutoSave.updateData(assessoridd,Stuid,t1.getText().toString(),optionb.getText().toString());
                    // Toast.makeText(getContext(),"Updated"+optionb.getText().toString(),Toast.LENGTH_LONG).show();
                }
                else {
                    dbAutoSave.insertData(assessoridd,Stuid,t1.getText().toString(),optionb.getText().toString());
                    // Toast.makeText(getContext(),"inserted"+optionb.getText().toString(),Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.option3:
                titlec.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_txt));
                idd=dbAutoSave.getDataOfSingleClient(query1,query);
                if (idd!=(null)){
                    dbAutoSave.updateData(assessoridd,Stuid,t1.getText().toString(),optionc.getText().toString());
                    // Toast.makeText(getContext(),"Updated"+optionc.getText().toString(),Toast.LENGTH_LONG).show();
                }
                else {
                    dbAutoSave.insertData(assessoridd,Stuid,t1.getText().toString(),optionc.getText().toString());
                    //  Toast.makeText(getContext(),"inserted"+optionc.getText().toString(),Toast.LENGTH_LONG).show();
                }
                break;
            case  R.id.option4:

                titled.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_txt));
                idd=dbAutoSave.getDataOfSingleClient(query1,query);
                if (idd!=(null)){
                    dbAutoSave.updateData(assessoridd,Stuid,t1.getText().toString(),optiond.getText().toString());
                    //  Toast.makeText(getContext(),"Updated"+optiond.getText().toString(),Toast.LENGTH_LONG).show();
                }
                else {
                    dbAutoSave.insertData(assessoridd,Stuid,t1.getText().toString(),optiond.getText().toString());
                    //  Toast.makeText(getContext(),"inserted"+optiond.getText().toString(),Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }    }
}
