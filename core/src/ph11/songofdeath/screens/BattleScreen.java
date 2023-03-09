package ph11.songofdeath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ph11.songofdeath.game.SongOfDeathGame;

public class BattleScreen extends AbstractScreen {
    private Table table;
    private TextButton attackButton;
    private Label characterInfo;

    private static String UI_TEXTUREATLAS_PATH = "assets/terra-mother-ui.atlas";
    private static String UI_SKIN_PATH = "assets/terra-mother-ui.json";
    private static TextureAtlas UI_TEXTUREATLAS = new TextureAtlas(UI_TEXTUREATLAS_PATH);
    private static Skin UI_SKIN = new Skin(Gdx.files.internal(UI_SKIN_PATH), UI_TEXTUREATLAS);
    public BattleScreen(SongOfDeathGame game) {
        super(game);
        stage = new Stage(new FitViewport(640, 800));
        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        table.bottom();
        stage.addActor(table);

        attackButton = new TextButton("Attack", UI_SKIN);
        characterInfo = new Label("Orpheus HP:50/50", UI_SKIN);
        table.add(attackButton).width(200);
        table.add(characterInfo).width(300);
    }
}
