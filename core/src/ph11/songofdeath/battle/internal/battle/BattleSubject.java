package ph11.songofdeath.battle.internal.battle;


import com.badlogic.gdx.utils.Array;
import ph11.songofdeath.battle.internal.entities.Entity;

public class BattleSubject {
    private Array<BattleObserver> Observers;

    public BattleSubject(){
        Observers = new Array<BattleObserver>();
    }

    public void addObserver(BattleObserver battleObserver){
        Observers.add(battleObserver);
    }

    public void removeObserver(BattleObserver battleObserver){
        Observers.removeValue(battleObserver, true);
    }

    protected void notify(final Entity entity, BattleObserver.BattleState event){
        for(BattleObserver observer: Observers){
            observer.onNotify(entity, event);
        }
    }
}
