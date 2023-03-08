package ph11.songofdeath.internal.item.equipment;

public class Armor extends Equipment {

    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    protected Armor(String name, String description, String type, int maxHP, int maxSP, int attack, int defense, int speed) {
        super(name, description, maxHP, maxSP, attack, defense, speed);
        this.type = type;
    }
    
}
