package com.jordanweaver.j_weaver_notifications_labthree;

import java.io.Serializable;

/**
 * Created by jordanweaver on 4/14/15.
 */
public class ArticleObject implements Serializable {

    String source;
    String title;
    String summary;
    String url;


    public ArticleObject(String source, String title, String summary, String url) {
        this.source = source;
        this.title = title;
        this.summary = summary;
        this.url = url;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
