package com.example.grocerylistjackthompson;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    //private ArrayAdapter<String> itemList;
    private GroceryAdapter itemList;
    private static final String FILE_NAME = "example.txt";
    private Path out;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> arrayList = load();
        ArrayList<Boolean> strikesList = loadStrikes();
        /*
        if(savedInstanceState != null) {
            Toast.makeText(this,"FJFIJFI", Toast.LENGTH_LONG);
            for(int i = 0; i < savedInstanceState.getInt("size"); i++) {
                arrayList.add(i, savedInstanceState.getString("" + i));
            }
        }*/


        setContentView(R.layout.activity_main);
        ListView groceryList = (ListView) findViewById(R.id.listArea);

        //instantiate custom adapter
        itemList = new GroceryAdapter(this, arrayList, strikesList);

        //itemList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        groceryList.setAdapter(itemList);

        final EditText edittext = (EditText) findViewById(R.id.editText);
        edittext.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    sendMessage(v);
                    return true;
                }
                return false;
            }
        });
        /*
        groceryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                itemList.remove(itemList.getItem(position));
            }
        });*/
    }

    /*
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<String> list= itemList.getList();

        outState.putInt("size", list.size());

        for(int i = 0; i < list.size(); i++) {
            outState.putString("" + i, list.get(i));
        }

    }*/

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void save() {
        /*String ser = SerializeObject.objectToString(itemList.getList());
        if (ser != null && !ser.equalsIgnoreCase("")) {
            SerializeObject.WriteSettings(act, ser, "myobject.dat");
        } else {
            SerializeObject.WriteSettings(act, "", "myobject.dat");
        }*/


        deleteFile(FILE_NAME);

        FileOutputStream fos = null;


        try {
            fos = openFileOutput(FILE_NAME, MODE_APPEND);


            ArrayList<String> list = itemList.getList();

            for(int i = 0; i < list.size(); i++) {
                fos.write(list.get(i).getBytes());
                fos.write(System.lineSeparator().getBytes());
            }

            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
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



        deleteFile("strikes.txt");

        FileOutputStream fos2 = null;


        try {
            fos2 = openFileOutput("strikes.txt", MODE_APPEND);


            ArrayList<Boolean> strikes = itemList.getStrikes();

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
    public ArrayList<String> load() {
        ArrayList<String> loadList = new ArrayList<String>();
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while((text = br.readLine()) != null) {
                sb.append(text).append("\n");

            }
            String[] parts = sb.toString().split(System.lineSeparator());

            for(int i = 0; i < parts.length; i++) {
                loadList.add(parts[i]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return loadList;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<Boolean> loadStrikes() {
        ArrayList<Boolean> strikesList = new ArrayList<>();

        FileInputStream fis = null;
        try {
            fis = openFileInput("strikes.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while((text = br.readLine()) != null) {
                sb.append(text).append("\n");

            }
            String[] parts = sb.toString().split(System.lineSeparator());

            for(int i = 0; i < parts.length; i++) {
                if(parts[i].equals("1")) {
                    strikesList.add(true);
                } else {
                    strikesList.add(false);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return strikesList;
    }
    /** Called when the user taps the Send button */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String item = editText.getText().toString();
        itemList.add(0,item);
        itemList.incrementFlags(view);
        itemList.notifyDataSetChanged();
        editText.setText("");
        save();
        /*
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, "Testing");
        startActivity(intent);
        */
    }
}
