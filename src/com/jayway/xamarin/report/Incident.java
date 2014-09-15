package com.jayway.xamarin.report;


import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.util.Base64;

public class Incident {

    private String name;
    private String description;
    private String image;

    public Incident(String name, String description, Bitmap bitmap) {
        this.name = name;
        this.description = description;


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        this.image= Base64.encodeToString(byteArrayOutputStream .toByteArray(), Base64.DEFAULT);
    }
}
