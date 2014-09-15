package com.jayway.xamarin.contacts;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jayway.xamarin.news.NewsActivity;
import com.jayway.xamarin.R;
import com.jayway.xamarin.report.IncidentActivity;

public class ContactActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        ContactsAdapter adapter = new ContactsAdapter(this, new ArrayList<Contact>());
        setListAdapter(adapter);

        registerForContextMenu(getListView());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    int position =  ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        Contact contact = (Contact) getListView().getAdapter().getItem(position);
        getMenuInflater().inflate(R.menu.contact, menu);
        menu.setHeaderTitle(contact.getName());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int position =  ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
        Contact contact = (Contact) getListView().getAdapter().getItem(position);

        switch(item.getItemId()) {
            case R.id.action_call:
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" + contact.getPhone()));
                startActivity(call);
                return true;
            case R.id.action_email:
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                email.putExtra(Intent.EXTRA_EMAIL, new String[] { contact.getEmail() });
                startActivity(Intent.createChooser(email, ""));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_contacts:
                return true;
            case R.id.action_news:
                startActivity(new Intent(this, NewsActivity.class));
                this.finish();
                return true;
            case R.id.action_report:
                startActivity(new Intent(this, IncidentActivity.class));
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        int itemPosition = position;
        Contact itemValue = (Contact) l.getItemAtPosition(position);
    }
}
