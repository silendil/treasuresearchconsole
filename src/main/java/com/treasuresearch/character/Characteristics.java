package com.treasuresearch.character;

import java.util.Arrays;
import java.util.List;

public enum Characteristics {
    Strength, Perception, Agility, Intelligence, Willpower, Health, Sanity, SpellCount, InventorySize, Speed, Defense,
    Resist;
    public static List<String> getBaseCharacteristics(){
        return Arrays.asList(new String[]{Strength.name(), Intelligence.name(),Willpower.name(),Perception.name(),
        Agility.name()});
    }
    public static List<String> getDerivedCharacteristics(){
        return Arrays.asList(new String[]{Health.name(),Sanity.name(),SpellCount.name(),InventorySize.name(),
                Speed.name(), Defense.name(), Resist.name()});
    }
}
