package ph11.songofdeath.battle.internal.item;

public class Consumables extends Item{
    private int restoredHP;
    private int restoredSP;

    public int getRestoredHP() {
        return this.restoredHP;
    }

    public void setRestoredHP(int restoredHP) {
        this.restoredHP = restoredHP;
    }

    public int getRestoredSP() {
        return this.restoredSP;
    }

    public void setRestoredSP(int restoredSP) {
        this.restoredSP = restoredSP;
    }

    public Consumables(String name, String description, int restoredHP, int restoredSP) {
        super(name, description);
        this.restoredHP = restoredHP;
        this.restoredSP = restoredSP;
    }
}
