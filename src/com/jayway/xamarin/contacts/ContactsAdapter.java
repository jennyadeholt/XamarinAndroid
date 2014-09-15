package com.jayway.xamarin.contacts;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jayway.xamarin.R;
import com.jayway.xamarin.server.SnalebodaServer;


public class ContactsAdapter extends ArrayAdapter<Contact> implements SnalebodaServer.ContactListener {

    private static class ViewHolder {
        TextView name;
        TextView number;
        TextView email;
    }

    private Activity context;
    private ArrayList<Contact> names;
    private SnalebodaServer server;

    public ContactsAdapter(Activity context, ArrayList<Contact> names) {
        super(context, R.layout.contact_listitem, names);
        this.context = context;
        this.names = names;

        server = SnalebodaServer.getInstance();

        server.addContactListener(this);
        server.getContacts();
    }

    @Override
    public void onContacts(ArrayList<Contact> contacts) {
        names = contacts;
        clear();
        addAll(contacts);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.contact_listitem, null);
            holder = new ViewHolder();
            holder.name = (TextView) rowView.findViewById(R.id.name);
            holder.number = (TextView) rowView.findViewById(R.id.number);
            holder.email = (TextView) rowView.findViewById(R.id.email);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        holder.name.setText(names.get(position).getName());
        holder.number.setText(names.get(position).getPhone());
        holder.email.setText(names.get(position).getEmail());


        return rowView;
    }
}
