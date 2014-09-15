package com.jayway.xamarin.news;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayway.xamarin.R;
import com.jayway.xamarin.server.SnalebodaServer;


public class NewsAdapter extends ArrayAdapter<News> implements SnalebodaServer.NewsListener{

    private static class ViewHolder {
        TextView title;
        TextView info;
    }

    private Activity context;
    private ArrayList<News> news;
    private SnalebodaServer server;

    public NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, R.layout.news_listitem, news);
        this.context = context;
        this.news = news;

        server = SnalebodaServer.getInstance();
        server.addNewsListener(this);
        server.getNews();
    }

    @Override
    public void onNews(ArrayList<News> news) {
        this.news = news;
        clear();
        addAll(news);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.news_listitem, null);
            holder = new ViewHolder();
            holder.title = (TextView) rowView.findViewById(R.id.title);
            holder.info = (TextView) rowView.findViewById(R.id.info);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        holder.title.setText(news.get(position).getTitle());
        holder.info.setText(news.get(position).getContent());

        return rowView;
    }
}
