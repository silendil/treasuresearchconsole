package com.treasuresearch.character;

import com.treasuresearch.labirint.Room;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Character {
    private boolean frenzy;
    private int level;
    private String name;
    private int actionPoints = 2;
    private int hitPoints;
    private boolean overload = false;
    private Map<String,Integer> baseCharacteristics = new HashMap<String, Integer>();
    private int freeSkills;
    private Map<String,Integer> derivedCharacteristics = new HashMap<String, Integer>();
    private boolean alive;
    private List<Item> inventory = new ArrayList<Item>();
    private Room room;

    private String[] directions = {"North","West","South","East"};



    public String move(String value)  {
        if(actionPoints == 0)
            return "You haven't action points!!!";
        String direction = getDirection(value);
        if(!Arrays.asList(directions).contains(direction))
            return value;
        if(!getAvailableDirections().contains(direction)){
            return "Impossible!!!";
        }
        room.setHero(null);
        Room target = null;
        try {
            target = (Room)Room.class.getMethod("get"+direction).invoke(room);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if(target != null)
            target.setHero(this);
        else
            return "Impossible!!!";
        room = target;
        actionPoints--;
        return "Done";
    }

    public String getDirection(String value) {
        for(String str : directions)
            if(str.equalsIgnoreCase(value))
                return str;
        return "Wrong direction";
    }

    public List<String> getAvailableDirections()  {
        List<String> dirs = new ArrayList<String>();
        for(String dir : directions){
            try {
                if((Boolean) Room.class.getMethod("has" + dir).invoke(room)){
                    dirs.add(dir);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return dirs;
    }

    public Character()
    {
        level = 1;
        name = "noname";
        setBaseCharacteristics(null);
        initDerivedCharacteristics();
        alive = true;
        frenzy = false;
    }

    public void setBaseCharacteristics(Map<String, Integer> baseCharacteristics) {
        if(baseCharacteristics == null) {
            baseCharacteristics = new HashMap<String, Integer>();
            for(String charName : Characteristics.getBaseCharacteristics())
                baseCharacteristics.put(charName,1);
        }
        int sum = 0;
        for(String charName : baseCharacteristics.keySet()) {
            if (baseCharacteristics.get(charName) <= 0)
                throw new NumberFormatException("Characteristics must be positive");
            sum += baseCharacteristics.get(charName);
        }
        if(level == 1){
            if(sum > baseCharacteristics.size() * 2)
                throw new NumberFormatException("Too many points of characteristics");
            else
                freeSkills = baseCharacteristics.size() * 2 - sum;
        }
        this.baseCharacteristics = baseCharacteristics;
    }
    public int getCharacteristic(String charName){
        if(Characteristics.getBaseCharacteristics().contains(charName))
            return baseCharacteristics.get(charName);
        else if(Characteristics.getDerivedCharacteristics().contains(charName))
            return derivedCharacteristics.get(charName);
        else
            throw new NumberFormatException("Wrong name of Characteristic");
    }
    public int getStrength(){
        return getCharacteristic(Characteristics.Strength.name());
    }
    public int getPerception(){
        return getCharacteristic(Characteristics.Perception.name());
    }
    public int getIntelligence(){
        return getCharacteristic(Characteristics.Intelligence.name());
    }
    public int getWillpower(){
        return getCharacteristic(Characteristics.Willpower.name());
    }
    public int getHealth(){
        return getCharacteristic(Characteristics.Health.name());
    }
    public int getAgility(){
        return getCharacteristic(Characteristics.Agility.name());
    }
    public int getSpeed(){
        return getCharacteristic(Characteristics.Speed.name());
    }
    public int getInventorySize(){
        return getCharacteristic(Characteristics.InventorySize.name());
    }
    private void changeSanity(int value){
        int currentSanity = getHealth() + value;
        if(currentSanity <= 0){
            frenzy = true;
            setSanity(0);
            characterFrenzy();
        }else{
            setSanity(currentSanity);
        }
    }
    public void increaseSanity(int value){
        if(value < 0)
            throw new NumberFormatException("Argument cannot be negative");
        changeSanity(value);
    }
    public void decreaseSanity(int value){
        if(value < 0)
            throw new NumberFormatException("Argument cannot be negative");
        changeSanity(-value);
    }

    private void characterFrenzy() {

    }

    private void changeHealth(int value){
        int currentHealth = getHealth() + value;
        if(currentHealth <= 0){
            hitPoints = 0;
            alive = false;
            setHealth(0);
            characterDead();
        }else{
            setHealth(currentHealth);
            if(hitPoints > currentHealth)
                hitPoints = currentHealth;
        }
    }


    public void decreaseHealth(int value){
        if(value < 0)
            throw new NumberFormatException("Argument cannot be negative");
        changeHealth(-value);
    }

    public void levelUp(){
        level++;
        increaseHealth((getStrength()/2+1)*4);
        increaseSanity(((getIntelligence()+getWillpower())/4 + 1)*4);
        freeSkills += getIntelligence()/4 + 3;
    }

    public void increaseHealth(int value) {
        if(value < 0)
            throw new NumberFormatException("Argument cannot be negative");
        changeHealth(value);
    }


    private void characterDead() {
    }
    private void setCharacteristic(String charName,int value){
        if(Characteristics.getBaseCharacteristics().contains(charName))
            baseCharacteristics.put(charName,value);
        else if(Characteristics.getDerivedCharacteristics().contains(charName))
            derivedCharacteristics.put(charName,value);
        else
            throw new NumberFormatException("Wrong name of Characteristic");
    }
    private void setHealth(int value){
        setCharacteristic(Characteristics.Health.name(),value);
    }
    private void setSanity(int value){
        setCharacteristic(Characteristics.Sanity.name(),value);
    }
    private void setStrength(int value){
        setCharacteristic(Characteristics.Strength.name(),value);
    }
    private void  setSpellCount(int value){
        setCharacteristic(Characteristics.SpellCount.name(), value);
    }
    private void setInventorySize(int value){
        setCharacteristic(Characteristics.InventorySize.name(),value);
    }
    private void setSpeed(int value){
        setCharacteristic(Characteristics.Speed.name(),value);
    }
    private void setDefense(int value){
        setCharacteristic(Characteristics.Defense.name(),value);
    }
    private void setResist(int value){
        setCharacteristic(Characteristics.Resist.name(),value);
    }
    private void changeStrength(int value){
        int currentStrength = getStrength() + value;
        if(currentStrength <= 0 )
            setStrength(1);
        setInventorySize(10 + getStrength() * 3);
        if(inventory.size() < getInventorySize())
            setOverload(true);
    }

    private void setOverload(boolean b) {
        if(b) {
            if (!overload)
                setSpeed(getSpeed() / 2);
        }else{
            if(overload)
                setSpeed(getAgility() / 5 + 2);
        }
        overload = b;
    }

    public void increaseStrength(int value){
        if(value < 0 )
            throw new NumberFormatException("Argument cannot be negative");
        changeStrength(value);
    }
    public void decreaseStrength(int value){
        if(value < 0 )
            throw new NumberFormatException("Argument cannot be negative");
        changeStrength(-value);
    }
    public void getDamage(int value){
        if (value < 0 && -value >= hitPoints)
        {
            hitPoints = 0;
            characterDead();
        }
        else
            hitPoints += value;
    }
    public void initDerivedCharacteristics(){
        setSanity((5+(getIntelligence()+getWillpower())/3)*4);
        setHealth((5+getStrength())*4);
        hitPoints = getHealth();
        setSpellCount(getIntelligence()*2);
        setInventorySize(10 + getStrength() * 3);
        setSpeed(getAgility() / 5 + 2);
        setDefense(getAgility() / 2);
        setResist(0);
    }
}
