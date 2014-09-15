package com.jayway.xamarin.news;

public class News {

    private String id;
    private String title;
    private String content;


    public News(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    public String getTitle() {
        return title;
    }
}
