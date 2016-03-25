package com.treasuresearch.character;

import java.util.HashMap;
import java.util.Map;

public class Item {
    private String name;
    private String description;
    private Map<String,Integer> bonus = new HashMap<String, Integer>();
    private int status;
    private int maxStatus;
    private boolean cracked = false;
    private boolean equipped = false;

    public Item() {
        name = "item";
        description = "no description";
        status = 5;
        maxStatus = 5;
    }


}
