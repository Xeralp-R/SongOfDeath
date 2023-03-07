package battle.entity;

import java.util.concurrent.ThreadLocalRandom;

import battle.location.Location;
import battle.skills.HealingSkills;

public class Enemy extends Entity {

    private String description;
    private Party enemyParty;
    private Location location;
    private int randomWeight;

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
    
    public Enemy(String name, String description, Location location, int randomWeight, int maxHP, int maxSP, int attack, int defense, int speed) {
        super(name, maxHP, maxSP, attack, defense, speed);
        this.randomWeight = randomWeight;
        this.description = description;
        this.location = location;
    }

    @Override
    public void castAOEHeal(HealingSkills targetSkill) {
        if(!targetSkill.isIsAOE()){
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
