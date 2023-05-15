package ph11.songofdeath.battle.internal.skills;

public class HealingSkill extends Skill {

    private double healingMultiplier;

    public double getHealingMultiplier() {
        return this.healingMultiplier;
    }

    public void setHealingMultiplier(double healingMultiplier) {
        this.healingMultiplier = healingMultiplier;
    }
    public HealingSkill(String name, String description, int strength, boolean AOE){
        super(name, description, strength, AOE);
    }

    public int healingCalculation(int userDefense, double healingMultiplier){
        return (int) (userDefense * healingMultiplier);
    }
    
}
