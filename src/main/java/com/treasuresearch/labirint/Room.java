package com.treasuresearch.labirint;

import com.treasuresearch.character.Character;

import java.util.ArrayList;

public class Room {
    private Room north;
    private Room west;
    private Room south;
    private Room east;
    private String name;
    private String discription;
    private int[] coord = new int[2];
    private Character hero;

    private boolean visible = false;
    private boolean investigated = false;
    private boolean exit = false;

    public Room(boolean exit) {
        this.exit = exit;
    }

    public void setCoord(int x, int y){
        coord[0] = x;
        coord[1] = y;
    }

    public int getX(){
        return coord[0];
    }

    public int getY(){
        return coord[1];
    }

    public void investigate(){
        investigated = true;
    }
    public void cloack(){
        investigated = false;
    }
    public void search(){
        visible = true;
    }

    public Room getNorth() {
        return north;
    }

    public void setNorth(Room north) {
        this.north = north;
    }

    public Room getWest() {
        return west;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public Room getSouth() {
        return south;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    public Room getEast() {
        return east;
    }

    public void setEast(Room east) {
        this.east = east;
    }

    public boolean hasNorth(){
        return north != null;
    }

    public boolean hasEast(){
        return east != null;
    }

    public boolean hasWest(){
        return west != null;
    }

    public boolean hasSouth(){
        return south != null;
    }

    public Room() {

    }

    public Character getHero() {
        return hero;
    }

    public void setHero(Character hero) {
        this.hero = hero;
    }

    public String getContent(){
        if(!visible)
            return "*";
        if(!investigated)
            return "?";
        else if(hero != null)
            return "H";
        else
            return " ";
    }
    public ArrayList<String> show(){
        ArrayList<String> out = new ArrayList<String>();
        StringBuilder row = new StringBuilder("#").append(hasNorth()?"-":"#").append("#");
        out.add(row.toString());
        row = new StringBuilder(hasWest()?"|":"#").append(getContent()).append(hasEast()?"|":"#");
        out.add(row.toString());
        row = new StringBuilder("#").append(hasSouth()?"-":"#").append("#");
        out.add(row.toString());
        return out;
    }
}
