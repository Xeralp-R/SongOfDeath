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
    private Entity actingEntity;
    private PartyMembers actingPartyMember;
    public PartyMembers getActingPartyMember(){
        return actingPartyMember;
    }

    private Enemy actingEnemy;
    public Enemy getActingEnemy(){
        return actingEnemy;
    }
    private static final int ENEMY_PARTY_SIZE = 1;
    private Entity targetEntity = null;

    public int getDamage() {
        return damage;
    }

    private int damage;

    private boolean battleFinished = true;

    public BattleManager(Party playerParty){
    }


    private void speedSort(ArrayList<Entity> battleEntities) {
        // Sort entities based on speed in descending order
        battleEntities.sort(new Comparator<Entity>() {
            @Override
            public int compare(Entity entity1, Entity entity2) {
                return Integer.compare(entity2.getEffectiveStats().getSpeed(), entity1.getEffectiveStats().getSpeed());
            }
        });
        Collections.reverse(battleEntities);
        System.out.println(battleEntities.get(0).getName() + " " + battleEntities.get(1).getName());
    }

    private void deadEntity(ArrayList<Entity> battleEntities, Entity targetEntity){
        battleEntities.remove(targetEntity);
    }

    public void decisionMaking(String decision){
        System.out.println(actingEntity.getName() + " Decisions being made!");
        switch(decision){
            case "ATTACK":
                if (actingEntity instanceof PartyMembers) {
                    damage = actingEntity.attack(actingEnemy);
                    notify(actingEntity, ATTACK);
                    notify(null, TURN_DONE);
                } else if (actingEntity instanceof Enemy) {
                    damage = actingEntity.attack(actingPartyMember);
                    notify(actingEntity, ATTACK);
                    notify(null, TURN_DONE);
                }
                break;
            case "DEFEND":
                actingEntity.guard();
                notify(actingEntity, GUARD);
                notify(null, TURN_DONE);
                break;
            case "RUN":
                run();
                break;
            default:
                if (targetEntity != null && actingEntity instanceof  Enemy && targetEntity instanceof  PartyMembers) {
                    damage = actingEntity.attack(targetEntity);
                }
                break;
        }
    }


    private void run(){
        notify(null, BATTLE_END);
        System.out.println("Running!");
    }

    private void turn(ArrayList<Entity> battleEntities, Party playerParty, Party enemyParty) {
        int listSize = battleEntities.size();

        for (int i = 0; i < listSize; i++) {
            actingEntity = battleEntities.get(i);

            if (actingEntity.getCurrentHP() == 0) {
                deadEntity(battleEntities, actingEntity);
                i--; // Adjust the index to account for the removed entity
                listSize--; // Adjust the list size accordingly
                continue;
            }

            if (actingEntity instanceof PartyMembers) {
                System.out.println("Player Notified!");
                notify(actingEntity, PLAYER_TURN_START);
            }


            if (i == listSize - 1) {
                // Move the entity with the highest speed to the beginning of the list
                Entity highestSpeedEntity = battleEntities.remove(i);
                battleEntities.add(0, highestSpeedEntity);
            }

            if(actingPartyMember.getCurrentHP() <= 0 || actingEnemy.getCurrentHP() <= 0){
                run();
                break;
            }

        }
    }

    public void initBattle(Party playerParty){
        Location battleLocation = playerParty.getPartyLocation();
        Location.EnemyList<Enemy> locationEnemyList = battleLocation.generateEnemyList();
        Party enemyParty = new Party(ENEMY_PARTY_SIZE);
        ArrayList<Entity> battleEntities = new ArrayList<>();

        for(int index = 0 ; index < ENEMY_PARTY_SIZE; index++){
            Enemy enemy = locationEnemyList.next();
            actingEnemy = new Enemy(enemy);
            enemyParty.getPartyList().add(enemy);
        }

        for(Entity entity: playerParty.getPartyList()){
            battleEntities.add(entity);
            actingPartyMember = (PartyMembers) entity;
            notify(entity, PLAYER_ADDED);
        }
        for(Entity entity: enemyParty.getPartyList()){
            battleEntities.add(entity);
            notify(entity, ENEMY_ADDED);
        }

        speedSort(battleEntities);

        turn(battleEntities, playerParty, enemyParty);

        if(actingPartyMember.getCurrentHP() <= 0 || actingEnemy.getCurrentHP() <= 0){
            run();
        }
    }
}