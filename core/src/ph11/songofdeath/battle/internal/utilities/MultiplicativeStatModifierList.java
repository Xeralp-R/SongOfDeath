package ph11.songofdeath.battle.internal.utilities;

import java.util.List;

public class MultiplicativeStatModifierList extends StatModifierList<Float> {
    private List<Float> modifiers;
    public MultiplicativeStatModifierList(List<Float> modifiers) {
        this.modifiers = modifiers;
    }

    @Override
    void setModifier(StatEnum stat_type, Float stat) {
        this.modifiers.set(stat_type.getValue(), stat);
    }

    @Override
    StatList applyModifiers(StatList given_stats) {
        for (StatEnum stat_value :
                StatEnum.values()) {
            int new_value = Math.round(given_stats.getStat(stat_value) * this.modifiers.get(stat_value.getValue()));
            given_stats.setStat(stat_value, new_value);
        }
        return given_stats;
    }
}
