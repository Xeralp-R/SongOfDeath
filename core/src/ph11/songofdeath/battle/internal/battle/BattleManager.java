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
    private Entity targetEntity = null;

    public boolean isBattleFinished() {
        return battleFinished;
    }

    public void setBattleFinished(boolean battleFinished) {
        this.battleFinished = battleFinished;
    }

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
    }

    private void deadEntity(ArrayList<Entity> battleEntities, Entity targetEntity){
        battleEntities.remove(targetEntity);
    }

    public void decisionMaking(String decision){
        System.out.println("Decisions being made!");
        switch(decision){
            case "ATTACK":
                notify(actingPartyMember, SELECT_TARGET); //put this in a while loop so the rest of the code doesnt run until targetentity isnt null
                if (targetEntity != null) {
                    actingPartyMember.attack(targetEntity);
                } else {
                    // Handle the case when no target entity is selected
                    System.out.println("No target entity selected for attack.");
                }
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

    private void turn(ArrayList<Entity> battleEntities, Party playerParty, Party enemyParty) {
        int listSize = battleEntities.size();

        for (int i = 0; i < listSize; i++) {
            Entity actingEntity = battleEntities.get(i);

            if (actingEntity.getCurrentHP() == 0) {
                deadEntity(battleEntities, actingEntity);
                i--; // Adjust the index to account for the removed entity
                listSize--; // Adjust the list size accordingly
                continue;
            }

            if (actingEntity instanceof PartyMembers) {
                actingPartyMember = (PartyMembers) actingEntity;
                System.out.println("Player Notified!");
                notify(actingEntity, PLAYER_TURN_START);
            }

            // Perform the action for the actingEntity

            if (i == listSize - 1) {
                // Move the entity with the highest speed to the beginning of the list
                Entity highestSpeedEntity = battleEntities.remove(i);
                battleEntities.add(0, highestSpeedEntity);
            }
        }
    }


    private void run(){
        runSuccessful = true;
    }

    public Entity selectTarget(int entityIndex){
        return enemyParty.getPartyList().get(entityIndex);
    }

    public Entity selectTarget(Entity targetEntity){
        return targetEntity;
    }

    public void initBattle(Party playerParty){
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
}
