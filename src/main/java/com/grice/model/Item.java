package com.grice.model;

import lombok.Data;

@Data
public class Item {

    private String link;
    private String icon;
    private String locale;
    private boolean blank;

    public Item() {
    }

    public Item(String link, String icon, String locale, boolean blank) {
        super();
        this.link = link;
        this.icon = icon;
        this.locale = locale;
        this.blank = blank;
    }

    @Override
    public String toString() {
        return "Item [link=" + link + ", icon=" + icon + ", locale=" + locale + ", blank=" + blank + "]";
    }

}
