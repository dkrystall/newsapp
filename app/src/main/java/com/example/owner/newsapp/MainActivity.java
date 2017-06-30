package com.example.owner.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.owner.newsapp.model.NewsItem;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // private EditText search;
    static final String TAG = "MainActivity";
    private ProgressBar progress;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Menu","onCreate etnered");
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_news);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.news_results);

       mNewsAdapter = new NewsAdapter();

       mRecyclerView.setHasFixedSize(true);
       mRecyclerView.setAdapter(mNewsAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        System.out.print("Entered createOptionsMenu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NewsTask net = null;
        switch (item.getItemId()) {
            case R.id.news_results:
                Log.i("Menu", "Refresh clicked");
                net = new NewsTask(""); // no query string, will refresh page
                net.execute();
                return true;
            case R.id.search:
                Log.i("Menu", "Search clicked");
                net = new NewsTask("bf84b88bcd544f8094c1083036bffaae"); // news api key as query string
                net.execute();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class NewsTask extends AsyncTask<URL, Void, ArrayList<NewsItem>> {
        String query;

        NewsTask(String s) {
            query = s;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.print("Entered onPreExecute");
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(URL... params) {
            URL url = null;
            try {
                url = new URL("http://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=bf84b88bcd544f8094c1083036bffaae");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            ArrayList<NewsItem> result = null;
            Log.d(TAG, "url: " + url.toString());
            try {
                String json = NetworkUtils.getResponseFromHttpUrl(url);
                result = NetworkUtils.parseJSON(json);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsItem> data) {
            super.onPostExecute(data);
            progress.setVisibility(View.GONE);
            if (data != null) {
                NewsAdapter adapter = new NewsAdapter(data, new NewsAdapter.ItemClickListener(){
                    @Override
                    public void onItemClick(int clickedItemIndex){ // add code here to finish
                        String url = data.get(clickedItemIndex).getUrl();
                        Log.d(TAG, String.format("Url %s", url));
                    }
                });
                mRecyclerView.setAdapter(adapter);

            }
        }
    }
}
