package ph11.songofdeath.battle.internal.battle;

import ph11.songofdeath.battle.internal.entities.Entity;

public interface BattleObserver {
    public static enum BattleState{

        ENEMY_ADDED,
        PLAYER_ADDED,
        PLAYER_TURN_START,
        TURN_DONE,
        ATTACK,
        BATTLE_END,
        GUARD
    }

    void onNotify(Entity enemyEntity, BattleState state);
}
