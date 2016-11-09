package com.cancercarecompany.ccc.ccc;

/**
 * Created by Josef on 2016-10-30.
 */
public class Article {

    int article_ID;
    String article_type;
    String title;
    String text;
    String source;
    String tag;
    String target_group;

    public Article(int article_ID, String article_type, String title, String text, String source, String tag, String target_group){

        this.article_ID = article_ID;
        this.article_type = article_type;
        this.title = title;
        this.text = text;
        this.source = source;
        this.tag = tag;
        this.target_group = target_group;
    }
}

