package com.example.myapplication;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GroceryAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;



    public GroceryAdapter(Context context) {
        this.list = new ArrayList<String>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        //return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
        return 0;
    }

    public void add(int index, String element) {
        list.add(index, element);
    }

    public void incrementFlags(View view) {
        /*int position = 0;

        for(int i = 0; i < list.size() - 1; i++) {
            // If current is strike through, next should be strike through

           // TextView listItemText = (TextView) findViewById(R.id.list_item_string);
            //if ((listItemText.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0) {
                //listItemText.setPaintFlags(listItemText.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            //} else {
              //  listItemText.setPaintFlags(listItemText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            //}
        }
        */
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grocery, null);
        }

        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        listItemText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TextView listItemText = (TextView)v.findViewById(R.id.list_item_string);
                if ((listItemText.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0) {
                    listItemText.setPaintFlags(listItemText.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                } else {
                    listItemText.setPaintFlags(listItemText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }

                notifyDataSetChanged();
            }
        });

        return view;
    }
}
