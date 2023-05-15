package ph11.songofdeath.battle.internal.entities;

import java.util.*;

import ph11.songofdeath.battle.internal.skills.DamageSkill;
import ph11.songofdeath.battle.internal.skills.HealingSkill;
import ph11.songofdeath.battle.internal.skills.Skill;
import ph11.songofdeath.battle.internal.utilities.StatList;
import ph11.songofdeath.battle.internal.utilities.*;

public abstract class Entity {
    private String name;
    private Stats baseStats;
    protected Stats totalStats;
    private Stats effectiveStats;
    private int currentHP;
    private int currentSP;
    private boolean guardActive;
    private boolean alive;
    private ArrayList<Skill> skillList;

    public final String ENTITY_SPRITE_PATH;

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Stats getBaseStats()
    {
        return this.baseStats;
    }
    public void setBaseStats(Stats baseStats)
    {
        this.baseStats = baseStats;
    }
    public int getCurrentHP()
    {
        return this.currentHP;
    }
    public void setCurrentHP(int currentHP)
    {
        this.currentHP = currentHP;
    }
    public int getCurrentSP()
    {
        return this.currentSP;
    }
    public void setCurrentSP(int currentSP)
    {
        this.currentSP = currentSP;
    }
    public Stats getTotalStats() {
        return this.totalStats;
    }
    public void setTotalStats(Stats totalStats) {
        this.totalStats = totalStats;
    }
    public Stats getEffectiveStats() {
        return this.effectiveStats;
    }
    public void setEffectiveStats(Stats effectiveStats) {
        this.effectiveStats = effectiveStats;
    }
    public boolean getGuardActive() {
        return guardActive;
    }
    public void setGuardActive(boolean guardActive){
        this.guardActive = guardActive;
    }
    public boolean getAlive(){
        return alive;
    }
    public void setAlive(boolean alive){
        this.alive = alive;
    }


    protected Entity(String name, int maxHP, int maxSP, int attack, int defense, int speed, String ENTITY_SPRITE_PATH){
        this.name = name;
        this.baseStats = new Stats(maxHP, maxSP, attack, defense, speed);
        this.currentHP = this.baseStats.getMaxHP();
        this.currentSP = this.baseStats.getMaxSP();
        this.effectiveStats = this.baseStats;
        this.totalStats = baseStats;
        this.skillList = new ArrayList<>();
        this.ENTITY_SPRITE_PATH = ENTITY_SPRITE_PATH;
    }

    public void addSkill(Skill targetSkill){
        if(this.skillList.contains(targetSkill)){
            return;
        }

        this.skillList.add(targetSkill);
    }

    public void removeSkill(Skill targetSkill){
        if(!this.skillList.contains(targetSkill)){
            return;
        }

        this.skillList.remove(targetSkill);
    }

    public static int healthCalculation(int healthChange, Entity targetEntity){
        int targetEntityCurrentHP = targetEntity.getCurrentHP();
        int targetEntityMaxHP = targetEntity.getTotalStats().getMaxHP();
        int theoreticalHP = targetEntityCurrentHP += healthChange;
        if(theoreticalHP >= targetEntityMaxHP){
            targetEntityCurrentHP = targetEntityMaxHP;
        }
        else if(theoreticalHP <= 0){
            targetEntityCurrentHP = 0;
            targetEntity.setAlive(false);
        }
        else{
            targetEntityCurrentHP = theoreticalHP;
        }
        return targetEntityCurrentHP;
    }

    public static int spCalculation(int spChange, Entity targetEntity){
        int targetEntityCurrentSP = targetEntity.getCurrentSP();
        int targetEntityMaxSP = targetEntity.getTotalStats().getMaxSP();
        int theoreticalSP = targetEntityCurrentSP += spChange;
        if(theoreticalSP >= targetEntityMaxSP){
            targetEntityCurrentSP = targetEntityMaxSP;
        }
        else if(theoreticalSP <= 0){
            targetEntityCurrentSP = 0;
        }
        else{
            targetEntityCurrentSP = theoreticalSP;
        }
        return targetEntityCurrentSP;
    }

    public void effectiveStatsCalculation(){
        int effectiveAttack =  (int) (this.totalStats.getAttack());
        int effectiveDefense = (int) (this.totalStats.getDefense());
        int effectiveSpeed = (int) (this.totalStats.getSpeed());

        this.effectiveStats = new Stats(this.totalStats.getMaxHP(), this.totalStats.getMaxSP(), effectiveAttack, effectiveDefense, effectiveSpeed);
    }

    public void attack(Entity targetEntity){
        int damage = DamageSkill.damageCalculation(this.getEffectiveStats().getAttack(), targetEntity.getEffectiveStats().getDefense(), 0.8, 0.5, targetEntity.getGuardActive());
        targetEntity.setCurrentHP(healthCalculation(damage*-1, targetEntity));

        System.out.printf("\nWow! %s did %d damage to %s\n", this.getName(), damage, targetEntity.getName());
    }

    public void guard(){
        this.guardActive = true;
    }

    public abstract Entity selectTarget(Party opposingParty);

    public void displaySkillList(){
        int listNumber = 1;
        for(Skill skills: this.skillList){
            System.out.printf("%d. %s %n", listNumber, skills.name);
            listNumber++;
        }
    }

    public void useSTDamageSkill(DamageSkill targetSkill, Entity targetEntity){
        if(targetSkill.isAOE()){
            return;
        }

        int damage = DamageSkill.damageCalculation(this.getEffectiveStats().getAttack(), targetEntity.getEffectiveStats().getDefense(),
                targetSkill.getDamageMultiplier(), targetSkill.getDefenseMultiplier(), targetEntity.getGuardActive());
        targetEntity.setCurrentHP(healthCalculation(damage*-1, targetEntity));
    }

    public void useSTHeal(HealingSkill targetSkill, Entity targetEntity){
        if(targetSkill.isAOE()){
            return;
        }

        int amountHealed = targetSkill.healingCalculation(this.getEffectiveStats().getDefense(), targetSkill.getHealingMultiplier());
        targetEntity.setCurrentHP(healthCalculation(amountHealed, targetEntity));
    }

    public abstract void castAOEHeal(HealingSkill targetSkill);

    public void castAOEDamage(DamageSkill targetSkill, Party opponentParty) {
        if(!targetSkill.isAOE()){
            return;
        }

        for(Entity entity: opponentParty.getPartyList()){
            int damage = DamageSkill.damageCalculation(this.getEffectiveStats().getAttack(), entity.getEffectiveStats().getDefense(),
                    targetSkill.getDamageMultiplier(), targetSkill.getDefenseMultiplier(), entity.getGuardActive());
            entity.setCurrentHP(healthCalculation(damage, entity));
        }

    }
}