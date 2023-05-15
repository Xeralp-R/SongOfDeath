package ph11.songofdeath.battle.internal.item;

import java.util.*;
import ph11.songofdeath.battle.internal.item.equipment.Equipment;

public class Bag {
    private static final ArrayList<Item> itemList = new ArrayList<>();
    private static final ArrayList<Equipment> equipmentList = new ArrayList<>();
    private static final ArrayList<Consumables> consumableList =  new ArrayList<>();

    public static ArrayList<Item> getItemList() {
        return itemList;
    }
    public static ArrayList<Equipment> getEquipmentList() {
        return equipmentList;
    }
    public static ArrayList<Consumables> getConsumableList() {
        return consumableList;
    }

    private Bag(){
        /*
         * Bag exists to hold the every item list, doesn't really have its own things that need to be created
         */
    }

    public static void pickUpItem(Item targetItem){
        Bag.itemList.add(targetItem);

        if(targetItem instanceof Equipment){
            Bag.equipmentList.add((Equipment)(targetItem));
        }
        if(targetItem instanceof Consumables){
            Bag.consumableList.add((Consumables)(targetItem));
        }
    }
}
