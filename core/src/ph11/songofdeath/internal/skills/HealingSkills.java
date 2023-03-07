package battle.skills;

public class HealingSkills extends Skills {

    private double healingMultiplier;

    public double getHealingMultiplier() {
        return this.healingMultiplier;
    }

    public void setHealingMultiplier(double healingMultiplier) {
        this.healingMultiplier = healingMultiplier;
    }

    public HealingSkills(String name, String description, double healingMultiplier, double attackModifier, double defenseModifier, double speedModifier, boolean isAOE) {
        super(name, description, attackModifier, defenseModifier, speedModifier, isAOE);
        this.healingMultiplier = healingMultiplier;
    }
    
}
