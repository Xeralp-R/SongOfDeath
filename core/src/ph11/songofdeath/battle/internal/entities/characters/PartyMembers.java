package ph11.songofdeath.battle.internal.entities.characters;

import ph11.songofdeath.battle.internal.entities.*;
import ph11.songofdeath.battle.internal.entities.Entity;
import ph11.songofdeath.battle.internal.entities.Party;
import ph11.songofdeath.battle.internal.item.Bag;
import ph11.songofdeath.battle.internal.item.Consumables;
import ph11.songofdeath.battle.internal.item.Item;
import ph11.songofdeath.battle.internal.item.equipment.Armor;
import ph11.songofdeath.battle.internal.item.equipment.Equipment;
import ph11.songofdeath.battle.internal.item.equipment.Weapon;
import ph11.songofdeath.battle.internal.skills.HealingSkill;

import java.util.*;


public class PartyMembers extends Entity {

    private Armor headArmor;
    private Armor bodyArmor;
    private Weapon weapon;
    private ArrayList<Equipment> equipmentList;
    private Stats bonusStats;
    protected static Party travellingParty = new Party();
    public static Party activeParty = new Party(4);

    public Armor getHeadArmor() {
        return this.headArmor;
    }

    public void setHeadArmor(Armor headArmor) {
        this.headArmor = headArmor;
    }

    public Armor getBodyArmor() {
        return this.bodyArmor;
    }

    public void setBodyArmor(Armor bodyArmor) {
        this.bodyArmor = bodyArmor;
    }

    public Weapon getWeapon() {
        return this.weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Stats getBonusStats() {
        return this.bonusStats;
    }

    public void setBonusStats(Stats bonusStats) {
        this.bonusStats = bonusStats;
    }

    public PartyMembers(String name, int maxHP, int maxSP, int attack, int defense, int speed, String CHARACTER_SPRITE_PATH) {
        super(name, maxHP, maxSP, attack, defense, speed, CHARACTER_SPRITE_PATH);
        equipmentList = new ArrayList<>();
        PartyMembers.travellingParty.getPartyList().add(this);
    }

    @Override
    public Entity selectTarget(Party opposingParty) {
        return null;
    }

    public void equipHeadArmor(Armor targetHeadArmor){
        if(targetHeadArmor.getType().equals("body")){
            System.out.println("You can't equip that here!");
            return;
        }
        int headArmorIndex = this.equipmentList.indexOf(this.headArmor);
        this.equipmentList.set(headArmorIndex, targetHeadArmor);
        this.headArmor = targetHeadArmor;
    }
    
    public void equipBodyArmor(Armor targetBodyArmor){
        if(targetBodyArmor.getType().equals("head")){
            System.out.println("You can't equip that here!");
            return;
        }
        int bodyArmorIndex = this.equipmentList.indexOf(this.bodyArmor);
        this.equipmentList.set(bodyArmorIndex, targetBodyArmor);
        this.bodyArmor = targetBodyArmor;
    }

    public void equipWeapon(Weapon targetWeapon){
        int weaponIndex = this.equipmentList.indexOf(this.weapon);
        this.equipmentList.set(weaponIndex, targetWeapon);
        this.weapon = targetWeapon;
    }

    public void bonusStatsCalculation(){
        int bonusMaxHP = 0;
        int bonusMaxSP = 0;
        int bonusAttack = 0;
        int bonusDefense = 0;
        int bonusSpeed = 0;
        for(Equipment e: this.equipmentList){
            bonusMaxHP += e.getEquipmentStats().getMaxHP();
            bonusMaxSP += e.getEquipmentStats().getMaxSP();
            bonusAttack += e.getEquipmentStats().getAttack();
            bonusDefense += e.getEquipmentStats().getDefense();
            bonusSpeed += e.getEquipmentStats().getSpeed();
        }

        bonusStats = new Stats(bonusMaxHP, bonusMaxSP, bonusAttack, bonusDefense, bonusSpeed);
    }

    public void totalStatsCalculation(){
        int totalMaxHP = this.getBaseStats().getMaxHP() + this.getBonusStats().getMaxHP();
        int totalMaxSP = this.getBaseStats().getMaxSP() + this.getBonusStats().getMaxSP();
        int totalAttack =this.getBaseStats().getAttack() + this.getBonusStats().getAttack();
        int totalDefense = this.getBaseStats().getDefense() + this.getBonusStats().getDefense();
        int totalSpeed = this.getBaseStats().getSpeed() + this.getBonusStats().getSpeed();

        this.totalStats = new Stats(totalMaxHP, totalMaxSP, totalAttack, totalDefense, totalSpeed);
    }


    public void addToActiveParty(){
        if(!PartyMembers.travellingParty.getPartyList().contains(this)){
            return;
        }
        if(PartyMembers.activeParty.getPartyList().contains(this)){
            return;
        }
        PartyMembers.activeParty.getPartyList().add(this);
    }

    public void removeFromActiveParty(){
        if(!PartyMembers.travellingParty.getPartyList().contains(this)){
            return;
        }
        if(!PartyMembers.activeParty.getPartyList().contains(this)){
            return;
        }
        PartyMembers.activeParty.getPartyList().remove(this);
    }

    public void displayItemList(){
        int listNumber = 1;
        for(Item item: Bag.getItemList()){
            System.out.printf("%d. %s %n", listNumber, item.getName());
        }
    }

    public void useConsumable(Consumables targetConsumable, PartyMembers targetPartyMember){
        if(!Bag.getConsumableList().contains(targetConsumable)){
            return;
        }

        if(!PartyMembers.activeParty.getPartyList().contains(targetPartyMember)){
            return;
        }

        targetPartyMember.setCurrentHP(healthCalculation(targetConsumable.getRestoredHP(), targetPartyMember));
        targetPartyMember.setCurrentSP(spCalculation(targetConsumable.getRestoredSP(), targetPartyMember));

        Bag.getConsumableList().remove(targetConsumable);
    }

    @Override
    public void castAOEHeal(HealingSkill targetSkill) {
        if(!targetSkill.isAOE()){
            return;
        }
        PartyMembers.activeParty.healParty(targetSkill.healingCalculation(this.getEffectiveStats().getDefense(), targetSkill.getHealingMultiplier()));
    }

}
