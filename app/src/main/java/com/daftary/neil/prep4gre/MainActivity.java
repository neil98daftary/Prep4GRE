package com.daftary.neil.prep4gre;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> words_letter = new ArrayList<String>();

    ArrayList<String> words = new ArrayList<String>();
    ArrayList<String> meanings = new ArrayList<String>();
    ArrayList<String> word_type = new ArrayList<String>();
    ArrayList<String> word_usage = new ArrayList<String>();
    TextView word_text;
    ListView listView;


    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlconnection = null;

            try {
                url = new URL(urls[0]);

                urlconnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlconnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String itemValue = getIntent().getStringExtra("letter");



        //Log.i("Words with A are", words.get(0));


        //word_text = (TextView) findViewById(R.id.word);
        listView= (ListView) findViewById(R.id.words_listview);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, words_letter);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // ListView Clicked item index
                int itemPosition     = i;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(i);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();
            }
        });





        DownloadTask task = new DownloadTask();
        String result = null;
        try {
            result = task.execute("https://script.googleusercontent.com/macros/echo?user_content_key=u0RxZnIoi3HQKmtaN54rE7R1uAsR6oYFiLp4jBYejYid_SVWuOvhTa6raNIsoi93QC_Q3WNqzOa8UrwDOCZ-eDXjp1YNI8zUOJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuBLhyHCd5hHa1ZsYSbt7G4nMhEEDL32U4DxjO7V7yvmJPXJTBuCiTGh3rUPjpYM_V0PJJG7TIaKpyaU5MQwmK_TgIj03uO37fI_L_J4O9M4MAEvK1YNBO5_TQoOsuRxbayQlvXGM4DKAcKiW3k6MDkf31SIMZH6H4k&lib=MbpKbbfePtAVndrs259dhPT7ROjQYJ8yx").get();
            Log.i("Result is:", result);

            JSONObject jsonObj = new JSONObject(result);

            // Getting JSON Array node
            JSONArray contacts = jsonObj.getJSONArray("Sheet1");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                String word = c.getString("Word");
                String meaning = c.getString("Meaning");
                String type = c.getString("Type");
                String usage = c.getString("Usage");

                words.add(word);
                meanings.add(meaning);
                word_type.add(type);
                word_usage.add(usage);



            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < words.size(); i++) {
            if(words.get(i).contains(itemValue))
            {
                words_letter.add(words.get(i));
            }
        }
        Log.i("Words with A are", words_letter.get(0));
    }
}
