package com.grice.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Navbar {

    private List<Item> items = new ArrayList<Item>();

    public void addItem(Item item) {
        items.add(item);
    }

}
