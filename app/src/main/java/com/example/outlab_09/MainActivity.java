package com.example.outlab_09;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // Members
    private ListView lv;
    // Contains the news data
    ArrayList<HashMap<String, String>> newsList;
    // Number of articles to display
    int nArticles = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsList = new ArrayList<>();
        lv = (ListView)findViewById(R.id.list);

        new GetData().execute();
    }

    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Downloading JSON data", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String apiUrl = "https://newsapi.org/v2/top-headlines?country=in&apiKey=aaba12eb04744b7291a942542bbd9bf0";
            String jsonStr = sh.makeServiceCall(apiUrl);
            Log.e("News App:", "JSON Response received: " + jsonStr);

            if (jsonStr != null){
                try{
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray articles = jsonObj.getJSONArray("articles");

                    // looping through all articles
                    for (int i = 0; i < nArticles; i++) {
                        JSONObject c = articles.getJSONObject(i);
                        String title = c.getString("title");
                        String author = c.getString("author");
                        String date = c.getString("publishedAt");
                        String url = c.getString("url");
                        // Temp HashMap
                        HashMap<String, String> tmpNews = new HashMap<>();
                        tmpNews.put("title", title);
                        tmpNews.put("author", author);
                        tmpNews.put("date", date);
                        // Add news to the list
                        newsList.add(tmpNews);
                    }
                }
                catch (final JSONException e) {
                    Log.e("News App:", "Error while parsing JSON: " + e.getMessage());
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            Toast.makeText(getApplicationContext(),"Error while parsing JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else {
                Log.e("News App:", "Null JSON object received");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        Toast.makeText(getApplicationContext(),"Null JSON object received", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this,
                    newsList,
                    R.layout.list_item, new String[]{"title", "author", "date"},
                    new int[]{R.id.title, R.id.author, R.id.date}
            );
            lv.setAdapter(adapter);
        }
    }
}