package com.jayway.xamarin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jayway.xamarin.contacts.ContactActivity;
import com.jayway.xamarin.news.NewsActivity;
import com.jayway.xamarin.report.IncidentActivity;

public class StartActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_contacts:
                startActivity(new Intent(this, ContactActivity.class));
                return true;
            case R.id.action_news:
                startActivity(new Intent(this, NewsActivity.class));
                return true;
            case R.id.action_report:
                startActivity(new Intent(this, IncidentActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
