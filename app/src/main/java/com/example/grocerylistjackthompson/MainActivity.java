package com.example.grocerylistjackthompson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

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

        final EditText edittext = (EditText) findViewById(R.id.editText);
        edittext.setOnKeyListener(new View.OnKeyListener() {
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
