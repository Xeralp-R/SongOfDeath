package ph11.songofdeath.battle.internal.location;

import java.util.*;
import ph11.songofdeath.battle.internal.entities.*;

public class Location {
    private String name;
    private ArrayList<Enemy> enemyList;

    public ArrayList<Enemy> getEnemyList() {
        return this.enemyList;
    }

    public void setEnemyList(ArrayList<Enemy> enemyList) {
        this.enemyList = enemyList;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location(String name){
        this.name = name;
        enemyList = new ArrayList<>();
    }

    public EnemyList<Enemy> generateEnemyList(){
        EnemyList<Enemy> randomEnemyList = new EnemyList<>();
        for(Enemy enemy: this.enemyList){
            randomEnemyList.add(enemy.getRandomWeight(), enemy);
        }

        return randomEnemyList;
    }

    //found this online to randomly generate based on weighted chance
    public class EnemyList<Enemy> {
        private final NavigableMap<Double, Enemy> map = new TreeMap<Double, Enemy>();
        private final Random random;
        private double total = 0;
    
        public EnemyList() {
            this(new Random());
        }
    
        public EnemyList(Random random) {
            this.random = random;
        }
    
        public EnemyList<Enemy> add(double weight, Enemy result) {
            if (weight <= 0) return this;
            total += weight;
            map.put(total, result);
            return this;
        }
    
        public Enemy next() {
            double value = random.nextDouble() * total;
            return map.higherEntry(value).getValue();
        }
    }
}
