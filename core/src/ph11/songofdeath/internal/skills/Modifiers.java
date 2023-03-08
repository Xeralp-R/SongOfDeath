package ph11.songofdeath.internal.skills;

public class Modifiers {
    //class of buffs/defbuffs

    private double attackModifier;
    private double defenseModifier;
    private double speedModifier;

    public double getAttackModifier() {
        return this.attackModifier;
    }

    public void setAttackModifier(double attackModifier) {
        this.attackModifier = attackModifier;
    }

    public double getDefenseModifier() {
        return this.defenseModifier;
    }

    public void setDefenseModifier(double defenseModifier) {
        this.defenseModifier = defenseModifier;
    }

    public double getSpeedModifier() {
        return this.speedModifier;
    }

    public void setSpeedModifier(double speedModifier) {
        this.speedModifier = speedModifier;
    }

    public Modifiers(){
        this.attackModifier = 1;
        this.defenseModifier = 1;
        this.speedModifier = 1;
    }

    public Modifiers(double attackModifier, double defenseModifier, double speedModifier){
        this.attackModifier = attackModifier;
        this.defenseModifier = defenseModifier;
        this.speedModifier = speedModifier;
    }
}
