package ph11.songofdeath.battle.internal.entities;

import ph11.songofdeath.battle.internal.location.Location;

import java.util.*;

/**
 * A general class that can contain entities, whether they are player or enemy.
 */
public class Party {
    private ArrayList<Entity> entityList;
    private int totalPartyHP = 0;
    private Location partyLocation;

    public Location getPartyLocation() { return this.partyLocation;}
    public void setPartyLocation(Location newLocation){ this.partyLocation = newLocation;}

    public int getTotalPartyHP() {
        return this.totalPartyHP;
    }
    public void setTotalPartyHP(int totalPartyHP) {
        this.totalPartyHP = totalPartyHP;
    }
    public ArrayList<Entity> getPartyList(){
        return entityList;
    }

    public Party(){
        entityList = new ArrayList<>();
    }

    public Party(int size){
        entityList = new ArrayList<>(size);
    }

    public void calculateTotalPartyHP(){
        totalPartyHP = 0;
        
        for(Entity entity: entityList){
            totalPartyHP += entity.getCurrentHP();
        }
    }

    public void healParty(int amountHealed){
        for(Entity entity: entityList){
            entity.setCurrentHP(Entity.healthCalculation(amountHealed, entity));
        }
    }

    public void fullHealParty(){
        for(Entity entity: entityList){
            entity.setCurrentHP(entity.getTotalStats().getMaxHP());
        }
    }
}
