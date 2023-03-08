package ph11.songofdeath.internal.skills;

import ph11.songofdeath.internal.utilities.*;

public abstract class Skill {
    public final String name;
    public final String description;
    public final int strength;
    public final StatModifierList modifiers;
    // very dubious. Maybe should be a marker interface on the subclasses...
    // still, that would take a lot of work, so, we're left with this.
    public final boolean isAOE;

    /**
     * The initializer for Skill. Newly revised to become a data object only.
     * @param name The name of the skill, as it should be displayed.
     * @param description A description of the skill.
     *                    TODO: make this more efficient, maybe a pointer to a text file, then call when needed?
     * @param strength The strength of the skill. What this does depends on the implementer.
     * @param modifiers The modifiers this skill gives the player, if any.
     * @param isAOE Whether this skill attacks everyone or is targeted.
     *              TODO: make more extendable. What about attacks 2, etc?
     */
    public Skill(String name, String description, int strength, StatModifierList modifiers, boolean isAOE){
        this.name = name;
        this.description = description;
        this.strength = strength;
        this.modifiers = modifiers;
        this.isAOE = isAOE;
    }

    /*
    public static int damageCalculation(int userAttack, int targetDefense, double damageMultiplier, double defenseMultiplier, boolean guardActive){
        int damage = (int) (damageMultiplier*(userAttack - (targetDefense * defenseMultiplier)));
        if(guardActive){
            damage -= damage*0.4;
        }
        return damage;
        
    }

    public int healingCalculation(int userDefense, double healingMultiplier){
        return (int) (userDefense * healingMultiplier);
    }*/
}
