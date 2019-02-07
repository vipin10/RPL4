package com.radiant.rpl.testa.ExamSection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.radiant.rpl.testa.LocalDB.DbAutoSave;

import radiant.rpl.radiantrpl.R;

/**
 * Created by DAT on 9/1/2015.
 */
public class FragmentParent extends Fragment {
   // private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
     TextView ttv;
     DbAutoSave dbAutoSave;
int positionn=0;
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
        adapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        //viewPager.setCurrentItem(adapter.);
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
                //Toast.makeText(getContext(),"ddd"+i,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    public void addPage(String pagename, String que) {
        Bundle bundle = new Bundle();
        bundle.putString("data", pagename);
        bundle.putString("daa",que);
        FragmentChild fragmentChild = new FragmentChild();
        fragmentChild.setArguments(bundle);
        adapter.addFrag(fragmentChild, pagename,que);
        adapter.notifyDataSetChanged();
        if (adapter.getCount() > 0)
            viewPager.setCurrentItem(0);
    }
}
