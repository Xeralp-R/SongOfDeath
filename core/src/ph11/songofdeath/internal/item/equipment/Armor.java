package ph11.songofdeath.internal.item.equipment;

import ph11.songofdeath.internal.utilities.StatModifierList;

public class Armor extends Equipment {
    public final ArmorType armorType;
    private final int armorSetCode;

    protected Armor(String name, String description, StatModifierList modifiers, ArmorType type, int armorSetCode) {
        super(name, description, modifiers);
        this.armorType = type;
        this.armorSetCode = armorSetCode;
    }
    
}
