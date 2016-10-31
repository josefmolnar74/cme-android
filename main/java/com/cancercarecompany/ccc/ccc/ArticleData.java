package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Josef on 2016-10-30.
 */
public class ArticleData implements Serializable {

    public ArrayList<Article> article_data;

    public ArticleData(ArrayList<Article> article_data) {
        this.article_data = article_data; //default value, does not matter because updated during read from database
    }
}