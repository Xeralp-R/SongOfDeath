package battle.skills;

public class DamageSkills extends Skills{

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

    public DamageSkills(String name, String description, double damageMultiplier, double defenseMultiplier, double attackModifier, double defenseModifier, double speedModifier, boolean isAOE){
        super(name, description, attackModifier, defenseModifier, speedModifier, isAOE);
        this.damageMultiplier = damageMultiplier;
        this.defenseMultiplier = defenseMultiplier;
    }
    
}
