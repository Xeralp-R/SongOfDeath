package ph11.songofdeath.internal.item.equipment;

import battle.entity.Stats;
import battle.item.Item;

public abstract class Equipment extends Item{

    private Stats equipmentStats;

    public Stats getEquipmentStats() {
        return this.equipmentStats;
    }

    public void setBonusStats(Stats equipmentStats) {
        this.equipmentStats = equipmentStats;
    }

    protected Equipment(String name, String description, int maxHP, int maxSP, int attack, int defense, int speed) {
        super(name, description);
        equipmentStats = new Stats(maxHP, maxSP, attack, defense, speed);
           
    }
    
}
