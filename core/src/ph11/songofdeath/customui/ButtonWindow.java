package ph11.songofdeath.customui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ph11.songofdeath.SongOfDeath;
import ph11.songofdeath.battle.internal.battle.BattleManager;
import ph11.songofdeath.battle.internal.battle.BattleObserver;
import ph11.songofdeath.battle.internal.entities.Entity;

public class ButtonWindow extends Window implements BattleObserver {

    private static final String UI_TEXTUREATLAS_PATH = "terra-mother-ui.atlas";
    private static final String UI_SKIN_PATH = "terra-mother-ui.json";
    private static TextureAtlas UI_TEXTUREATLAS = new TextureAtlas(UI_TEXTUREATLAS_PATH);
    private static Skin UI_SKIN = new Skin(Gdx.files.internal(UI_SKIN_PATH), UI_TEXTUREATLAS);

    private final TextButton attackButton;
    private final TextButton skillButton;
    private final TextButton defendButton;
    private final TextButton itemButton;
    private final TextButton runButton;
    public ButtonWindow(String title, final BattleManager battleManager) {
        super(title, UI_SKIN);

        attackButton = new TextButton("Attack", UI_SKIN);
        attackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                Gdx.app.exit();
                battleManager.decisionMaking("ATTACK");
            }
        });

        skillButton = new TextButton("Skill", UI_SKIN);
        defendButton = new TextButton("Defend", UI_SKIN);
        defendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                battleManager.decisionMaking("DEFEND");
            }
        });
        itemButton = new TextButton("Item", UI_SKIN);
        runButton = new TextButton("Run", UI_SKIN);

        Table table = new Table();
        table.add(attackButton).pad(20,20,20,20);
        table.row();
        table.add(skillButton).pad(20,20,20,20);
        table.row();
        table.add(defendButton).pad(20,20,20,20);
        table.row();
        table.add(skillButton).pad(20,20,20,20);
        table.row();
        table.add(itemButton).pad(20,20,20,20);
        table.row();
        table.add(runButton).pad(20,20,20,20);
        table.row();

        this.add(attackButton);
    }

    @Override
    public void onNotify(Entity enemyEntity, BattleState state) {
        switch(state){
            case PLAYER_TURN_START:
                attackButton.setDisabled(false);
                defendButton.setDisabled(false);
                runButton.setDisabled(false);
                System.out.println("Turn started!");
                break;
        }
    }
}
