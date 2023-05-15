package ph11.songofdeath.battle.internal.utilities;

public enum StatEnum {
    MaxHP(0),
    MaxSP(1),
    Attack(2),
    Defense(3),
    Speed(4);

    private int value;
    StatEnum(int i) {this.value = i;}
    public int getValue() {return this.value;}
}
