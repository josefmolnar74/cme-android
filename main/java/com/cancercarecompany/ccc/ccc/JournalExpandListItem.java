package com.cancercarecompany.ccc.ccc;

import java.util.Comparator;

/**
 * Created by Josef on 2016-12-01.
 */
public class JournalExpandListItem {

    public String header;
    public Sideeffect sideeffect;
    public HealthData healthData;

    public JournalExpandListItem(String headline, Sideeffect sideeffect, HealthData healthData) {

        this.header = headline;
        this.sideeffect = sideeffect;
        this.healthData = healthData;
    }

    /*Comparator for sorting the list by Name*/
    public static Comparator<JournalExpandListItem> journalItemComparator = new Comparator<JournalExpandListItem>() {

        public int compare(JournalExpandListItem s1, JournalExpandListItem s2) {
            String header1 = s1.header.toUpperCase();
            String header2 = s2.header.toUpperCase();

            //ascending order
            return header1.compareTo(header2);

        }};
}
