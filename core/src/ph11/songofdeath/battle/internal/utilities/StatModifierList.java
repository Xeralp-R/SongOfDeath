package ph11.songofdeath.battle.internal.utilities;

public abstract class StatModifierList<Q> {
    abstract void setModifier(StatEnum stat_type, Q stat);

    abstract StatList applyModifiers(final StatList given_stats);
}
