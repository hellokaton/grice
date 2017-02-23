package com.grice.model;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean isBlank() {
        return blank;
    }

    public void setBlank(boolean blank) {
        this.blank = blank;
    }

    @Override
    public String toString() {
        return "Item [link=" + link + ", icon=" + icon + ", locale=" + locale + ", blank=" + blank + "]";
    }

}
