package ph11.songofdeath.internal.battle;


import ph11.songofdeath.internal.entities.Party;
import ph11.songofdeath.internal.location.Location;

public class BattleManager {


    public BattleManager(Party playerParty, Location battleLocation){
        boolean battleFinished = false;
        Location.EnemyList<Enemy> locationEnemyList = battleLocation.generateEnemyList();
        Party enemyParty = new Party(4);
        ArrayList<Entity> battleEntities = new ArrayList<>();

        for(int index = 0 ; index <4; index++){
            Enemy enemy = locationEnemyList.next();
            enemyParty.getPartyList().add(enemy);
        }

        for(Entity entity: playerParty.getPartyList()){
            battleEntities.add(entity);
        }
        for(Entity entity: enemyParty.getPartyList()){
            battleEntities.add(entity);
        }

        do{
            playerParty.calculateTotalPartyHP();
            enemyParty.calculateTotalPartyHP();

            System.out.println(playerParty.getTotalPartyHP());
            System.out.println(enemyParty.getTotalPartyHP());

            if(playerParty.getTotalPartyHP() == 0 || enemyParty.getTotalPartyHP() <= 0){
                battleFinished = true;
            }
            turn(battleEntities, playerParty, enemyParty);
            System.out.println(battleFinished);
        }
        while(!battleFinished);
    }

    private void speedSort(ArrayList<Entity> battleEntities){
        Map<Integer, Entity> speedMap = new HashMap<Integer, Entity>();
        int index = 0;
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

    private void decisionMaking(PartyMembers actingPartyMember, Party enemyParty){
        System.out.println("What do you want to do? Input the number beside the action");
        System.out.println("1. Attack");
        System.out.println("2. Defend");
        System.out.println("3. Use Skills");
        System.out.println("4. Use Items");

        String decision = sc.nextLine();
        switch(decision){
            case "1":
                actingPartyMember.attack(actingPartyMember.selectTarget(enemyParty));
                break;
            case "2":
                actingPartyMember.guard();
                break;
            case "3":
                actingPartyMember.displaySkillList();
                break;
            case "4":
                actingPartyMember.displayItemList();
                break;
            default:
                actingPartyMember.attack(actingPartyMember.selectTarget(enemyParty));
                break;
        }
    }

    private void turn(ArrayList<Entity> battleEntities, Party playerParty, Party enemyParty){
        int listNumber = 1;
        for(Entity entity: battleEntities){
            System.out.printf("%d. %s %n", listNumber, entity.getName());
            listNumber++;
        }
        speedSort(battleEntities);
        for(int index = 0; index < battleEntities.size(); index++){
            Entity actingEntity = battleEntities.get(index);
            if(actingEntity.getCurrentHP() == 0){
                deadEntity(battleEntities, actingEntity);
                break;
            }

            if(actingEntity instanceof PartyMembers){
                decisionMaking((PartyMembers)(actingEntity), enemyParty);
            }
            else{
                actingEntity.attack(actingEntity.selectTarget(playerParty));
            }
        }
        return;
    }
}
