package ph11.songofdeath.internal.utilities;

import java.util.List;

/**
 * A stat modifier list whose stat modifiers are additive.
 * All values must be taken as ints: the presumption is that, the final values for these
 * modifiers must always be integers.
 */
public class AdditiveStatModifierList extends StatModifierList<Integer> {
    private List<Integer> modifiers;

    /**
     * Constructs a stat modifier list from another array of stat modifiers,
     * which should be constructed first. A
     * @param statModifiers
     */
    AdditiveStatModifierList(List<Integer> statModifiers) {
        this.modifiers = statModifiers;
    }

    /**
     * Changes one of the modifiers in this list
     * @param stat_type the type of statistic.
     * @param stat the actual value that will be changed.
     */
    @Override
    public void setModifier(StatEnum stat_type, Integer stat) {
        this.modifiers.set(stat_type.getValue(), stat);
    }

    /**
     * Applies the modifiers in this class on the given statistics list.
     * 
     * @param givenStats the stats that will be modified.
     * @return the modified statList.
     */
    @Override
    public StatList applyModifiers(StatList givenStats) {
        for (StatEnum stat_value :
                StatEnum.values()) {
            int new_value = givenStats.getStat(stat_value) + this.modifiers.get(stat_value.getValue());
            givenStats.setStat(stat_value, new_value);
        }
        return givenStats;
    }
}
