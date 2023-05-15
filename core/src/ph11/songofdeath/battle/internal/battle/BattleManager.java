package ph11.songofdeath.battle.internal.battle;


import ph11.songofdeath.SongOfDeath;
import ph11.songofdeath.battle.internal.entities.Party;
import ph11.songofdeath.battle.internal.entities.characters.PartyMembers;
import ph11.songofdeath.battle.internal.location.Location;
import ph11.songofdeath.battle.internal.entities.*;
import ph11.songofdeath.screens.BattleScreen;

import java.util.*;

import static ph11.songofdeath.battle.internal.battle.BattleObserver.BattleState.*;


// CUT DOWN TO ONLY WORK FOR ONE (1) PLAYER CHARACTER AND ONE (1) ENEMY RIGHT NOW , WILL BE UPDATED TO WORK FOR UP TO 4 FOR BOTH
public class BattleManager extends BattleSubject {
    private Party playerParty;
    private PartyMembers actingPartyMember;
    private Party enemyParty;
    private static final int ENEMY_PARTY_SIZE = 1; // WILL BE INCREASED TO FOUR IN THE FUTURE

    private boolean runSuccessful = false;

    public boolean isBattleFinished() {
        return battleFinished;
    }

    public void setBattleFinished(boolean battleFinished) {
        this.battleFinished = battleFinished;
    }

    private boolean battleFinished = true;

    public BattleManager(Party playerParty){
        Location battleLocation = playerParty.getPartyLocation();
        Location.EnemyList<Enemy> locationEnemyList = battleLocation.generateEnemyList();
        Party enemyParty = new Party(ENEMY_PARTY_SIZE);
        ArrayList<Entity> battleEntities = new ArrayList<>();

        for(int index = 0 ; index < ENEMY_PARTY_SIZE; index++){
            Enemy enemy = locationEnemyList.next();
            enemyParty.getPartyList().add(enemy);
        }

        for(Entity entity: playerParty.getPartyList()){
            battleEntities.add(entity);
            notify(entity, PLAYER_ADDED);
        }
        for(Entity entity: enemyParty.getPartyList()){
            battleEntities.add(entity);
            notify(entity, ENEMY_ADDED);
        }

        do{
            playerParty.calculateTotalPartyHP();
            enemyParty.calculateTotalPartyHP();

            if(playerParty.getTotalPartyHP() == 0 || enemyParty.getTotalPartyHP() <= 0 || runSuccessful){
                battleFinished = true;
                notify(null, BATTLE_END);
            }
            turn(battleEntities, playerParty, enemyParty);
        }
        while(!battleFinished);
    }

    private void speedSort(ArrayList<Entity> battleEntities){
        Map<Integer, Entity> speedMap = new HashMap<Integer, Entity>();
        int index = 0;
        System.out.println("Speed sorted!");
        for(Entity entity: battleEntities){
            speedMap.put(entity.getEffectiveStats().getSpeed(), entity);
        }
        Map<Integer, Entity> sortedSpeedMap = new TreeMap<Integer, Entity>(
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2){
                        return o2.compareTo(o1);
                    }
                }
        );

        for(Entity entity: sortedSpeedMap.values()){
            battleEntities.set(index, entity);
            index++;
        }
    }

    private void deadEntity(ArrayList<Entity> battleEntities, Entity targetEntity){
        battleEntities.remove(targetEntity);
    }

    public void decisionMaking(String decision){
        System.out.println("Decisions being made!");
        switch(decision){
            case "ATTACK":
                actingPartyMember.attack(actingPartyMember.selectTarget(enemyParty));
                break;
            case "DEFEND":
                actingPartyMember.guard();
                break;
            case "SKILL":
                actingPartyMember.displaySkillList();
                break;
            case "ITEM":
                actingPartyMember.displayItemList();
                break;
            case "RUN":
                run();
            default:
                actingPartyMember.attack(actingPartyMember.selectTarget(enemyParty));
                break;
        }
    }

    private void turn(ArrayList<Entity> battleEntities, Party playerParty, Party enemyParty){
        int listNumber = 1;
        speedSort(battleEntities);
        for(int index = 0; index < battleEntities.size(); index++){
            Entity actingEntity = battleEntities.get(index);
            if(actingEntity.getCurrentHP() == 0){
                deadEntity(battleEntities, actingEntity);
                break;
            }

            if(actingEntity instanceof PartyMembers){
                System.out.println("Player Notified!");
                notify(actingEntity, PLAYER_TURN_START);

                System.out.println("Waiting for notification");
            }

            /*
            if(actingEntity instanceof PartyMembers){
                decisionMaking(,(PartyMembers)(actingEntity), enemyParty);
            }
            else{
                actingEntity.attack(actingEntity.selectTarget(playerParty));
            }
             */
        }
        return;
    }

    private void run(){
        runSuccessful = true;
    }
}
