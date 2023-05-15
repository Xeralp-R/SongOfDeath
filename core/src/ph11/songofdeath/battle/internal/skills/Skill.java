package ph11.songofdeath.battle.internal.skills;

import ph11.songofdeath.battle.internal.utilities.StatModifierList;
import ph11.songofdeath.battle.internal.utilities.*;

public abstract class Skill {
    public final String name;
    public final String description;
    public final int strength;
    // very dubious. Maybe should be a marker interface on the subclasses...
    // still, that would take a lot of work, so, we're left with this.
    public final boolean AOE;

    /**
     * The initializer for Skill. Newly revised to become a data object only.
     * @param name The name of the skill, as it should be displayed.
     * @param description A description of the skill.
     *                    TODO: make this more efficient, maybe a pointer to a text file, then call when needed?
     * @param strength The strength of the skill. What this does depends on the implementer.
     * @param AOE Whether or not the skill hits one person or everyone
     */
    public Skill(String name, String description, int strength, boolean AOE){
        this.name = name;
        this.description = description;
        this.strength = strength;
        this.AOE = AOE;
    }

    public boolean isAOE() {
        return AOE;
    }

}
