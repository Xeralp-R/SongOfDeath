package ph11.songofdeath.battle.internal.entities.characters;

public class Player extends PartyMembers {

    protected Player(String name, int maxHP, int maxSP, int attack, int defense, int speed, String CHARACTER_SPRITE_PATH) {
        super(name, maxHP, maxSP, attack, defense, speed, CHARACTER_SPRITE_PATH);
    }
    
}
