package com.cancercarecompany.ccc.ccc;

/**
 * Created by 23047371 on 2016-09-26.
 */
import java.util.Comparator;
public class SideeffectExpandListItem {

    public String header;
    public Sideeffect sideeffect;

    public SideeffectExpandListItem(String headline, Sideeffect sideeffect) {

        this.header = headline;
        this.sideeffect = sideeffect;
    }

    /*Comparator for sorting the list by Student Name*/
    public static Comparator<SideeffectExpandListItem> SideEffectItemComparator = new Comparator<SideeffectExpandListItem>() {

        public int compare(SideeffectExpandListItem s1, SideeffectExpandListItem s2) {
            String header1 = s1.header.toUpperCase();
            String header2 = s2.header.toUpperCase();

            //ascending order
            return header1.compareTo(header2);

        }};
}