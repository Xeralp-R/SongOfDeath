package ph11.songofdeath.internal.item.equipment;

import ph11.songofdeath.internal.item.Item;
import ph11.songofdeath.internal.utilities.StatModifierList;

public abstract class Equipment extends Item {
    public final StatModifierList modifiers;

    protected Equipment(String name, String description, StatModifierList modifiers) {
        super(name, description);
        this.modifiers = modifiers;
           
    }
}
