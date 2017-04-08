package com.daftary.neil.prep4gre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Letters extends AppCompatActivity {

    ArrayList<String> letters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letters);
        final ListView listView1 = (ListView) findViewById(R.id.letters_listview);
        letters = new ArrayList<String>();
        char a = 'A';

        for (int i=0; i<26; i++)
        {
            letters.add(String.valueOf(a));
            a++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, letters);


        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int itemPosition     = i;

                // ListView Clicked item value
                String  itemValue    = (String) listView1.getItemAtPosition(i);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_SHORT)
                        .show();

                Intent intent =  new Intent(Letters.this, MainActivity.class);
                intent.putExtra("letter", itemValue);
                Letters.this.startActivity(intent);

            }
        });


    }
}
