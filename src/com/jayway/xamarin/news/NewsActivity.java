package com.jayway.xamarin.news;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.jayway.xamarin.R;
import com.jayway.xamarin.report.ReportActivity;
import com.jayway.xamarin.contacts.ContactActivity;

public class NewsActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        NewsAdapter adapter = new NewsAdapter(this, new ArrayList<News>());
        setListAdapter(adapter);
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
                this.finish();
                return true;
            case R.id.action_news:
                return true;
            case R.id.action_report:
                startActivity(new Intent(this, ReportActivity.class));
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        int itemPosition     = position;
        String  itemValue    = (String) l.getItemAtPosition(position);
    }
}

