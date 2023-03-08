package ph11.songofdeath.internal.item.equipment;

import ph11.songofdeath.internal.utilities.StatModifierList;

public class Weapon extends Equipment {
    public final int strength;

    public Weapon(String name, String description, int strength, StatModifierList modifiers) {
        super(name, description, modifiers);
        this.strength = strength;
    }

}
