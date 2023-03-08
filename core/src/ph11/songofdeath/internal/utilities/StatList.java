package ph11.songofdeath.internal.utilities;

import java.util.ArrayList;
import java.util.List;

public class StatList {

        List<Integer> stats = new ArrayList<>();

        public StatList(int maxHP, int maxSP, int attack, int defense, int speed){
            this.stats.set(StatEnum.MaxHP.getValue(), maxHP);
            this.stats.set(StatEnum.MaxSP.getValue(), maxSP);
            this.stats.set(StatEnum.Attack.getValue(), attack);
            this.stats.set(StatEnum.Defense.getValue(), defense);
            this.stats.set(StatEnum.Speed.getValue(), speed);
        }

        public void setStat(StatEnum stat_type, int value) {
            this.stats.set(stat_type.getValue(), value);
        }

        public int getStat(StatEnum stat_type) {
            return this.stats.get(stat_type.getValue());
        }
}
