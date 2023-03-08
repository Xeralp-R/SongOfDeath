package ph11.songofdeath.internal.item;

public class Consumables extends Item{
    public final int restoredHP;
    public final int restoredSP;

    public Consumables(String name, String description, int restoredHP, int restoredSP) {
        super(name, description);
        this.restoredHP = restoredHP;
        this.restoredSP = restoredSP;
    } 
}
