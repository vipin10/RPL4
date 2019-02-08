package com.radiant.rpl.testa.ExamSection;

import android.widget.BaseAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import radiant.rpl.radiantrpl.R;

public class CustomAdapter extends BaseAdapter {
    String result[];
    Context con;
    private static final int TYPE_HEADER = 1;

    private static LayoutInflater inflater = null;

    public CustomAdapter(String[] result, Context con, int[] img) {
        this.result = result;
        this.con = con;

        inflater = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final holder  hld = new holder();
        View rowview;
        if (position == TYPE_HEADER) {
            //Inflating recycle view item layout
           // View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
            rowview = inflater.inflate(R.layout.header,null);
        } else {
            rowview = inflater.inflate(R.layout.gdmainfortestquestion, null);
            hld.tv = rowview.findViewById(R.id.txt2);
            hld.tv.setText(result[position]);
            hld.tv.setTextColor(Color.WHITE);
        }
        return rowview;
    }

    public class holder
    {
        TextView tv;

    }

}

