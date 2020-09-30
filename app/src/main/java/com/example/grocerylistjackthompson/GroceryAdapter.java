package com.example.grocerylistjackthompson;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.Context.MODE_APPEND;

class GroceryObject {
    String item;
    boolean strike;

    public GroceryObject(String gItem, boolean gStrike) {
        item = gItem;
        strike = gStrike;
    }

    public int compareTo(GroceryObject o) {
        return item.compareTo(o.item);
    }

    public static Comparator<GroceryObject> GCompare = new Comparator<GroceryObject>() {

        public int compare(GroceryObject o1, GroceryObject o2) {
            return o1.item.compareTo(o2.item);
        }
    };
}

public class GroceryAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<Boolean> strikes = new ArrayList<Boolean>();
    private Context context;



    public GroceryAdapter(Context context, ArrayList<String> array, ArrayList<Boolean> strikesL) {
        this.context = context;
        list = array;

        strikes = strikesL;
        /*
        strikes = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            strikes.add(false);
        }*/
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
        strikes.add(index, false);
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

    public ArrayList<String> getList() {
        return list;
    }

    public ArrayList<Boolean> getStrikes() {
        return strikes;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void save() {
        /*String ser = SerializeObject.objectToString(itemList.getList());
        if (ser != null && !ser.equalsIgnoreCase("")) {
            SerializeObject.WriteSettings(act, ser, "myobject.dat");
        } else {
            SerializeObject.WriteSettings(act, "", "myobject.dat");
        }*/

        String FILE_NAME = "example.txt";

        context.deleteFile(FILE_NAME);

        FileOutputStream fos = null;


        try {
            fos = context.openFileOutput(FILE_NAME, MODE_APPEND);


            ArrayList<String> list = getList();

            for(int i = 0; i < list.size(); i++) {
                fos.write(list.get(i).getBytes());
                fos.write(System.lineSeparator().getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        context.deleteFile("strikes.txt");

        FileOutputStream fos2 = null;


        try {
            fos2 = context.openFileOutput("strikes.txt", MODE_APPEND);


            ArrayList<Boolean> strikes = getStrikes();

            for(int i = 0; i < strikes.size(); i++) {
                fos2.write(strikes.get(i)? "1".getBytes() : "0".getBytes());
                fos2.write(System.lineSeparator().getBytes());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos2 != null) {
                try {
                    fos2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void sort() {
        ArrayList<GroceryObject> g = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            GroceryObject item = new GroceryObject(list.get(i), strikes.get(i));
            g.add(item);
        }

        Collections.sort(g, GroceryObject.GCompare);

        list.clear();
        strikes.clear();

        for(int i = 0; i < g.size(); i++) {
            list.add(g.get(i).item);
            strikes.add(g.get(i).strike);
        }
        /*
        for(int i = 0; i < strikes.size(); i++) {
            strikes.set(i,false);
        }*/
        notifyDataSetChanged();
        save();
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

        if(strikes.get(position)) {
            listItemText.setPaintFlags(listItemText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            listItemText.setPaintFlags(listItemText.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                list.remove(position);
                strikes.remove(position);
                notifyDataSetChanged();
                save();
            }
        });

        listItemText.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if(strikes.get(position)) {
                    strikes.set(position, false);
                } else {
                        strikes.set(position,true);
                }
                notifyDataSetChanged();
                save();
            }
        });

        return view;
    }
}
