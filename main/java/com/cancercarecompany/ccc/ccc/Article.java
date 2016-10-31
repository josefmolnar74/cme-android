package com.cancercarecompany.ccc.ccc;

/**
 * Created by Josef on 2016-10-30.
 */
public class Article {

    int article_ID;
    String info_type;
    String title;
    String content;
    String date;
    String keyword;

    public Article(int article_ID, String info_type, String title, String content, String date, String keyword){

        this.article_ID = article_ID;
        this.info_type = info_type;
        this.title = title;
        this.content = content;
        this.date = date;
        this.keyword = keyword;
    }
}

