package ph11.songofdeath.battle.internal.entities;

import ph11.songofdeath.battle.internal.location.Location;
import ph11.songofdeath.battle.internal.skills.*;

import java.util.concurrent.ThreadLocalRandom;



public class Enemy extends Entity {

    private String description;
    private Location location;
    private int randomWeight;
    public final double enemyID;
    private Party enemyParty;

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Location getLocation() {
        return this.location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public int getRandomWeight() {
        return this.randomWeight;
    }
    public void setRandomWeight(int randomWeight) {
        this.randomWeight = randomWeight;
    }


    // try factory method for this
    public Enemy(String name, String description, Location location, int randomWeight, int maxHP, int maxSP, int attack, int defense, int speed, String ENEMY_SPRITE_PATH) {
        super(name, maxHP, maxSP, attack, defense, speed, ENEMY_SPRITE_PATH);
        this.randomWeight = randomWeight;
        this.description = description;
        this.location = location;
        this.enemyID = (int)(Math.random() * 100);
    }

    public Enemy(Enemy enemy){
        super(enemy.getName(), enemy.getBaseStats().getMaxHP(),enemy.getBaseStats().getMaxSP(), enemy.getBaseStats().getAttack(), enemy.getBaseStats().getDefense(), enemy.getBaseStats().getSpeed(), enemy.ENTITY_SPRITE_PATH );
        this.randomWeight = enemy.getRandomWeight();
        this.description = enemy.getDescription();
        this.location = enemy.getLocation();
        this.enemyID = enemy.enemyID;
    }

    /*
    public void copyEnemy(Enemy targetEnemy){
        this = new Enemy(targetEnemy);

    }
    */

    @Override
    public void castAOEHeal(HealingSkill targetSkill) {
        if(!targetSkill.isAOE()){
            return;
        }
        this.enemyParty.healParty(targetSkill.healingCalculation(this.getEffectiveStats().getDefense(), targetSkill.getHealingMultiplier()));
    }
    @Override
    public Entity selectTarget(Party opposingParty) {
        int index = ThreadLocalRandom.current().nextInt(0, opposingParty.getPartyList().size());
        return opposingParty.getPartyList().get(index); 
    }
}
