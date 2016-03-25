package com.treasuresearch.labirint;

import javax.json.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Maze {
    private List<List<Room>> field = new ArrayList<List<Room>>();
    private int verticalSize;
    private int horizontalSize;
    private int minVerticalVisible, maxVerticalVisible, minHorizontalVisible, maxHorizontalVisible;

    public Room getRoomByIndex(int x, int y){
        if(y >= 0 && y < verticalSize && x >= 0 && field.get(y).size() > x)
            return field.get(y).get(x);
        else
            return null;
    }
    public String getContent(int x, int y){
        if(getRoomByIndex(x,y) == null) {
            if (x >= minHorizontalVisible && x < maxHorizontalVisible
                    && y >= minVerticalVisible && y < maxVerticalVisible)
                return "#";
            else
                return "*";
        }
        else
            return getRoomByIndex(x,y).getContent();
    }

    public Maze() {

    }

    public boolean isLoadedField(){
        return (field != null && field.size() != 0);
    }

    public int getHorizontalSize() {
        return horizontalSize;
    }

    public int getVerticalSize() {
        return verticalSize;
    }

    public String loadField(InputStream is){
        JsonReader reader = Json.createReader(is);
        JsonObject object = reader.readObject();
        JsonArray rows = object.getJsonArray("rows");
        int maxLength = 0;
        JsonObject wall = Json.createObjectBuilder().add("type","wall").build();
        JsonObject room = Json.createObjectBuilder().add("type","room").build();
        JsonObject exit = Json.createObjectBuilder().add("type","exit").build();
        for(JsonValue row : rows){
            JsonArray cols = ((JsonObject)row).getJsonArray("cols");
            ArrayList<Room> mazeRow = new ArrayList<Room>();
            for(JsonValue value : cols){
                if(value.equals(wall)){
                    mazeRow.add(null);
                }else if(value.equals(room)){
                    mazeRow.add(new Room());
                }else if(value.equals(exit)){
                    mazeRow.add(new Room(true));
                }
            }
            if(maxLength < mazeRow.size())
                maxLength = mazeRow.size();
            field.add(mazeRow);
        }
        verticalSize = field.size();
        minVerticalVisible = 0;
        maxVerticalVisible = verticalSize;
        horizontalSize = maxLength;
        minHorizontalVisible = 0;
        maxHorizontalVisible = horizontalSize;
        initRelatives();
        return "success";
    }
    public void initRelatives(){
        for(int i = 0; i < verticalSize; i ++){
            for(int j = 0; j < horizontalSize; j ++){
                if(getRoomByIndex(j,i) != null){
                    getRoomByIndex(j,i).setCoord(j,i);
                    getRoomByIndex(j,i).setNorth(getRoomByIndex(j,i-1));
                    getRoomByIndex(j,i).setSouth(getRoomByIndex(j,i+1));
                    getRoomByIndex(j,i).setWest(getRoomByIndex(j-1,i));
                    getRoomByIndex(j,i).setEast(getRoomByIndex(j+1,i));
                }
            }
        }
    }
    public ArrayList<String> showRoom(int x, int y){
        if(getContent(x,y).equalsIgnoreCase("#")||getContent(x,y).equalsIgnoreCase("*")){
            StringBuilder row = new StringBuilder(getContent(x,y)).append(getContent(x,y)).append(getContent(x,y));
            ArrayList<String> out = new ArrayList<String>();
            out.add(row.toString());
            out.add(row.toString());
            out.add(row.toString());
            return out;
        }
        else {
            return getRoomByIndex(x,y).show();
        }
    }
    public ArrayList<ArrayList<String>> showWebArray(){
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        for(int i = minVerticalVisible; i <= maxVerticalVisible; i++){
            ArrayList<String> row = new ArrayList<String>(),item;
            for(int j = minHorizontalVisible; j <= maxHorizontalVisible; j++){
                item = showRoom(j,i);
                row.add(item.get(0) + "<br>" + item.get(1) + "<br>" + item.get(2));
            }
            result.add(row);
        }
        return result;
    }
}
