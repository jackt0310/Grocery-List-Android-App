package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    //private ArrayAdapter<String> itemList;
    private GroceryAdapter itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView groceryList = (ListView) findViewById(R.id.listArea);

        //instantiate custom adapter
        itemList = new GroceryAdapter(this);

        //itemList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        groceryList.setAdapter(itemList);

        /*
        groceryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                itemList.remove(itemList.getItem(position));
            }
        });*/
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String item = editText.getText().toString();
        itemList.add(0,item);
        itemList.incrementFlags(view);
        itemList.notifyDataSetChanged();
        editText.setText("");
        /*
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, "Testing");
        startActivity(intent);
        */
    }
}
