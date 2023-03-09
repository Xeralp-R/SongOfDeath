package ph11.songofdeath.game;

import com.badlogic.gdx.Game;
import ph11.songofdeath.screens.BattleScreen;

public class SongOfDeathGame extends Game {

    private static BattleScreen battleScreen;

    @Override
    public void create() {
        battleScreen = new BattleScreen(this);
        setScreen(battleScreen);
    }

    @Override
    public void dispose() {
        battleScreen.dispose();
    }
}
