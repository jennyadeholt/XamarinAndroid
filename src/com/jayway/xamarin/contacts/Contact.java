package com.jayway.xamarin.contacts;

public class Contact {

    private String id;
    private String name;
    private String phone;
    private String email;


    public Contact(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName(){
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}

