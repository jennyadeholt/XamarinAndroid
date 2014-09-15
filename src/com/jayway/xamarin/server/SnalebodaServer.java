package com.jayway.xamarin.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.xamarin.contacts.Contact;
import com.jayway.xamarin.news.News;

public class SnalebodaServer {

    public interface ContactListener {
        void onContacts(ArrayList<Contact> contacts);
    }

    public interface NewsListener {
        void onNews(ArrayList<News> news);
    }

    private String url = "https://snaleboda.azure-mobile.net/tables/";
    private ContactListener contactListener;
    private NewsListener newsListener;

    private static SnalebodaServer instance;

    public static SnalebodaServer getInstance() {
        if (instance == null) {
            instance = new SnalebodaServer();
        }
        return instance;
    }

    public void addContactListener(ContactListener contactListener) {
        this.contactListener = contactListener;
    }

    public void addNewsListener(NewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public void getContacts() {
        new ContactsTask().execute();
    }

    public void getNews() {
        new NewsTask().execute();
    }

    private String getJSON(String data) {
        String result = "";
        try {
            URL u = new URL(url + data);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.connect();

            switch (c.getResponseCode()) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    result = sb.toString();
            }

        } catch (MalformedURLException ex) {

        } catch (IOException ex) {

        }

        return result;
    }

    private class ContactsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return getJSON("contacts");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ArrayList<Contact> contacts = new Gson().fromJson(result, new TypeToken<ArrayList<Contact>>(){}.getType());

            for (Contact c : contacts) {
                Log.d("", c.getName());
            }
            if (contactListener != null){
                contactListener.onContacts(contacts);
            }
        }
    }

    private class NewsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return getJSON("news");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ArrayList<News> news = new Gson().fromJson(result, new TypeToken<ArrayList<News>>(){}.getType());

            for (News c : news) {
                Log.d("", c.getContent());
            }
            if (newsListener != null) {
                newsListener.onNews(news);
            }
        }
    }
}
