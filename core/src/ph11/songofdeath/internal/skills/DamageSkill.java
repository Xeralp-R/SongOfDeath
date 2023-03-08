package ph11.songofdeath.internal.skills;

import ph11.songofdeath.internal.utilities.StatModifierList;

public class DamageSkill extends Skill {
    public DamageSkill(String name, String description, int strength, StatModifierList list, boolean isAOE){
        super(name, description, strength, list, isAOE);
    }
}
