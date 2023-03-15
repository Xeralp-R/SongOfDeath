package ph11.songofdeath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ph11.songofdeath.game.SongOfDeathGame;

public class BattleScreen extends AbstractScreen {
    private Table table;
    private TextButton attackButton;
    private TextButton skillButton;
    private TextButton defendButton;
    private TextButton itemButton;
    private TextButton runButton;
    private Label trashLabel;
    private Label characterInfo1;
    private Label characterInfo2;
    private Label characterInfo3;
    private Label characterInfo4;
    private Label characterInfo5;
    private Dialog optionsDialog;
    private Dialog infoDialog;
    private Image characterSprite;
    private Image enemySprite;

    private static final String UI_TEXTUREATLAS_PATH = "terra-mother-ui.atlas";
    private static final String UI_SKIN_PATH = "terra-mother-ui.json";
    private static TextureAtlas UI_TEXTUREATLAS = new TextureAtlas(UI_TEXTUREATLAS_PATH);
    private static Skin UI_SKIN = new Skin(Gdx.files.internal(UI_SKIN_PATH), UI_TEXTUREATLAS);
    private static final String CHARACTER_SPRITE_PATH = "character.png";
    private static Texture CHARACTER_TEXTURE = new Texture(CHARACTER_SPRITE_PATH);
    private static final String ENEMY_SPRITE_PATH = "enemy.png";
    private static Texture ENEMY_TEXTURE = new Texture(ENEMY_SPRITE_PATH);


    private static final int OPTIONS_DIALOG_WIDTH = 300;
    public BattleScreen(SongOfDeathGame game) {
        super(game);
        stage = new Stage(new ScreenViewport());
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        trashLabel = new Label("", UI_SKIN);

        characterSprite = new Image(CHARACTER_TEXTURE);
        enemySprite = new Image(ENEMY_TEXTURE);
        table.add(characterSprite).center();
        table.add(enemySprite).center();
        table.row();
        table.add(trashLabel).height(75);
        table.row();

        optionsDialog = new Dialog("", UI_SKIN, "window-player");
        attackButton = new TextButton("Attack", UI_SKIN);
        skillButton = new TextButton("Skill", UI_SKIN);
        defendButton = new TextButton("Defend", UI_SKIN);
        itemButton = new TextButton("Item", UI_SKIN);
        runButton = new TextButton("Run", UI_SKIN);
        table.add(optionsDialog).width(200).bottom();

        optionsDialog.getButtonTable().add(attackButton);
        optionsDialog.getButtonTable().row();
        optionsDialog.getButtonTable().add(skillButton);
        optionsDialog.getButtonTable().row();
        optionsDialog.getButtonTable().add(defendButton);
        optionsDialog.getButtonTable().row();
        optionsDialog.getButtonTable().add(itemButton);
        optionsDialog.getButtonTable().row();
        optionsDialog.getButtonTable().add(runButton);

        infoDialog = new Dialog("", UI_SKIN, "window-player");
        characterInfo1 = new Label("CharacterName HP 50/50 MP 20/20", UI_SKIN);
        characterInfo2 = new Label("CharacterName HP 50/50 MP 20/20", UI_SKIN);
        characterInfo3 = new Label("CharacterName HP 50/50 MP 20/20", UI_SKIN);
        characterInfo4 = new Label("CharacterName HP 50/50 MP 20/20", UI_SKIN);
        characterInfo5 = new Label("CharacterName HP 50/50 MP 20/20", UI_SKIN);
        table.add(infoDialog).width(400).bottom();

        infoDialog.getContentTable().add(characterInfo1).left();
        infoDialog.getContentTable().row();
        infoDialog.getContentTable().add(characterInfo2).left();
        infoDialog.getContentTable().row();
        infoDialog.getContentTable().add(characterInfo3).left();
        infoDialog.getContentTable().row();
        infoDialog.getContentTable().add(characterInfo4).left();
        infoDialog.getContentTable().row();
        infoDialog.getContentTable().add(characterInfo5).left();

    }
}
