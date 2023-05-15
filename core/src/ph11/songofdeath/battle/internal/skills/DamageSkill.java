package ph11.songofdeath.battle.internal.skills;

import ph11.songofdeath.battle.internal.utilities.StatModifierList;

public class DamageSkill extends Skill {

    private double damageMultiplier;
    private double defenseMultiplier;

    public double getDamageMultiplier() {
        return this.damageMultiplier;
    }

    public void setDamageMultiplier(double damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    public double getDefenseMultiplier() {
        return this.defenseMultiplier;
    }

    public void setDefenseMultiplier(double defenseMultiplier) {
        this.defenseMultiplier = defenseMultiplier;
    }
    public DamageSkill(String name, String description, int strength, double damageMultiplier, double defenseMultiplier, boolean AOE){
        super(name, description, strength, AOE);
        this.damageMultiplier = damageMultiplier;
        this.defenseMultiplier = defenseMultiplier;
    }

    public static int damageCalculation(int userAttack, int targetDefense, double damageMultiplier, double defenseMultiplier, boolean guardActive){
        int damage = (int) (damageMultiplier*(userAttack - (targetDefense * defenseMultiplier)));
        if(guardActive){
            damage -= damage*0.4;
        }
        return damage;

    }
}
