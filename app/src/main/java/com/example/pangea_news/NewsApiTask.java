// NewsApiTask.java
package com.example.pangea_news;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsApiTask extends AsyncTask<String, Void, String> {

    private static final String API_KEY = "17e5786f01adec6fc3b5c4421cf147d1";
    private static final String BASE_URL = "https://gnews.io/api/v4/search?q=%s&token=%s";

    private NewsApiListener listener;

    public NewsApiTask(NewsApiListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String query = params[0];

        try {
            URL url = new URL(String.format(BASE_URL, query, API_KEY));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } catch (IOException e) {
            Log.e("NewsApiTask", "Error fetching data", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (listener != null) {
            listener.onNewsFetched(result);
        }
    }

    public interface NewsApiListener {
        void onNewsFetched(String result);
    }
}
