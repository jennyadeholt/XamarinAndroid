package com.jayway.xamarin.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jayway.xamarin.contacts.Contact;
import com.jayway.xamarin.news.News;
import com.jayway.xamarin.report.Incident;

public class SnalebodaServer {

    public interface ContactListener {
        void onContacts(ArrayList<Contact> contacts);
    }

    public interface NewsListener {
        void onNews(ArrayList<News> news);
    }

    public interface IncidentListener {
        void onIncidentSent(boolean result);
    }

    private String APPLICATION_KEY = "RyRNAoNAhIIaDeboNqfwwdxJZSuQyi32";

    private String url = "https://snaleboda.azure-mobile.net/tables/";

    private ContactListener contactListener;
    private NewsListener newsListener;
    private IncidentListener incidentListener;

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

    public void addIncidentListener(IncidentListener incidentListener) {
        this.incidentListener = incidentListener;
    }

    public void sendIncident(Incident incident) {
       new IncidentTask().execute(incident);
    }

    public void getContacts() {
        new ContactsTask().execute();
    }

    public void getNews() {
        new NewsTask().execute();
    }


    private class IncidentTask extends AsyncTask<Incident, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Incident... voids) {
            Boolean result = false;
            Incident incident = voids[0];

            if (incident != null) {
                Gson gson = new Gson();
                String json = gson.toJson(incident);

                try {
                    HttpPost httpPost = new HttpPost(url + "incidents");
                    httpPost.setEntity(new StringEntity(json));
                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setHeader("Content-type", "application/json");
                    httpPost.setHeader("X-ZUMO-APPLICATION", APPLICATION_KEY);

                    HttpResponse response = new DefaultHttpClient().execute(httpPost);

                    switch (response.getStatusLine().getStatusCode()) {
                        case 201:
                            Log.d("Xamarin", "Gotten response " + response.getStatusLine().getStatusCode());
                            result = Boolean.TRUE;
                            break;
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.d("Xamarin", "Done");
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (incidentListener != null) {
                incidentListener.onIncidentSent(result);
            }
        }
    }

    private class ContactsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return getJSON("contacts");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (contactListener != null) {
                ArrayList<Contact> contacts = new Gson().fromJson(result, new TypeToken<ArrayList<Contact>>() {
                }.getType());
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

            if (newsListener != null) {
                ArrayList<News> news = new Gson().fromJson(result, new TypeToken<ArrayList<News>>() {
                }.getType());
                newsListener.onNews(news);
            }
        }
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
                        sb.append(line + "\n");
                    }
                    br.close();
                    result = sb.toString();
            }

        } catch (MalformedURLException ex) {

        } catch (IOException ex) {

        }

        return result;
    }
}
