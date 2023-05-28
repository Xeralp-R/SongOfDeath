package ph11.songofdeath.battle.internal.battle;

import ph11.songofdeath.battle.internal.entities.Entity;

public interface BattleObserver {
    public static enum BattleState{

        BATTLE_START,
        ENEMY_ADDED,
        PLAYER_ADDED,
        PLAYER_TURN_START,
        PLAYER_TURN_DONE,
        PLAYER_HIT,
        ENEMY_HIT,
        BATTLE_END,
        SELECT_TARGET
    }

    void onNotify(Entity enemyEntity, BattleState state);
}
