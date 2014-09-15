package com.jayway.xamarin.report;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jayway.xamarin.R;
import com.jayway.xamarin.contacts.ContactActivity;
import com.jayway.xamarin.news.NewsActivity;
import com.jayway.xamarin.server.SnalebodaServer;

public class IncidentActivity extends Activity implements SnalebodaServer.IncidentListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView image;
    private TextView name;
    private TextView description;

    private ProgressBar progressBar;

    private Bitmap bitmap;

    private SnalebodaServer server;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        image = (ImageView) findViewById(R.id.image);
        name = (TextView) findViewById(R.id.name);
        description = (TextView) findViewById(R.id.description);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        server = SnalebodaServer.getInstance();
        server.addIncidentListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void takePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void sendReport(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Incident incident = new Incident(name.getText().toString(), description.getText().toString(), bitmap);
        server.sendIncident(incident);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_contacts:
                startActivity(new Intent(this, ContactActivity.class));
                finish();
                return true;
            case R.id.action_news:
                startActivity(new Intent(this, NewsActivity.class));
                finish();
                return true;
            case R.id.action_report:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onIncidentSent(boolean result) {
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Rapporten har " + (result ? "" : "inte ") + "skickats" , Toast.LENGTH_LONG).show();

        if (result) {
            description.setText("");
            image.setImageResource(android.R.drawable.ic_menu_camera);
        }
    }
}
