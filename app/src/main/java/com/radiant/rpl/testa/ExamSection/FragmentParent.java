package com.radiant.rpl.testa.ExamSection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.radiant.rpl.testa.LocalDB.DbAutoSave;

import radiant.rpl.radiantrpl.R;

/**
 * Created by DAT on 9/1/2015.
 */
public class FragmentParent extends Fragment {
    // private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    TextView ttv,mrreview,resetque;
    DbAutoSave dbAutoSave;
    String dummystuidd="1";
    int positionn=0;
    String queidd,queiddd;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent, container, false);
        dbAutoSave=new DbAutoSave(getContext());
        getIDs(view);

        //setEvents();
        return view;
    }

    private void getIDs(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.my_viewpager);
        mrreview=view.findViewById(R.id.mark);
        resetque=view.findViewById(R.id.reset);
        adapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            Boolean first = true;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (!first && positionOffset == 0 && positionOffsetPixels == 0){
                    onPageSelected(0);
                    first = false;
                }
            }
            @Override
            public void onPageSelected(int position) {
                int i=viewPager.getCurrentItem();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mrreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("stuid is"+queidd+" queidd is"+queiddd);
                dbAutoSave.insertDataunanswered(queidd,dummystuidd,"2");
            }
        });
    }

    public void addPage(String pagename, String que, String op1, String op2, String op3, String op4) {
        Bundle bundle = new Bundle();
        bundle.putString("data", pagename);
        bundle.putString("daa",que);
        bundle.putString("op1",op1);
        bundle.putString("op2",op2);
        bundle.putString("op3",op3);
        bundle.putString("op4",op4);
        FragmentChild fragmentChild = new FragmentChild();
        fragmentChild.setArguments(bundle);
        adapter.addFrag(fragmentChild, pagename,que);
        adapter.notifyDataSetChanged();
        if (adapter.getCount() > 0)
            viewPager.setCurrentItem(0);
    }
}
