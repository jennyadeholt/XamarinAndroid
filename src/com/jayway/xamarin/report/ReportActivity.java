package com.jayway.xamarin.report;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.jayway.xamarin.R;
import com.jayway.xamarin.contacts.ContactActivity;
import com.jayway.xamarin.news.NewsActivity;

public class ReportActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void takePicture(View view) {

    }

    public void sendReport(View view) {

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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
