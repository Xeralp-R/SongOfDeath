package ph11.songofdeath.internal.utilities;

public class StatList {

        public int maxHP;
        public int maxSP;
        public int attack;
        public int defense;
        public int speed;

        public StatList(int maxHP, int maxSP, int attack, int defense, int speed){
            this.maxHP = maxHP;
            this.maxSP = maxSP;
            this.attack = attack;
            this.defense = defense;
            this.speed = speed;
        }

        public int getMaxHP(){
            return maxHP;
        }
        public int getMaxSP(){
            return maxSP;
        }
        public int getAttack(){
            return attack;
        }
        public int getDefense(){
            return defense;
        }
        public int getSpeed(){
            return speed;
        }
}