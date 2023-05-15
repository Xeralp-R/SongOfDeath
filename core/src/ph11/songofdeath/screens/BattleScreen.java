package ph11.songofdeath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ph11.songofdeath.SongOfDeath;
import ph11.songofdeath.battle.internal.battle.BattleManager;
import ph11.songofdeath.battle.internal.battle.BattleObserver;
import ph11.songofdeath.battle.internal.entities.Entity;
import ph11.songofdeath.battle.internal.entities.characters.PartyMembers;

public class BattleScreen extends AbstractScreen implements BattleObserver{
    private Table battleScreenTable;
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
    private Dialog optionsDialog;
    private Dialog infoDialog;
    private Image characterSprite;
    private Image enemySprite;

    private static final String UI_TEXTUREATLAS_PATH = "terra-mother-ui.atlas";
    private static final String UI_SKIN_PATH = "terra-mother-ui.json";
    private static TextureAtlas UI_TEXTUREATLAS = new TextureAtlas(UI_TEXTUREATLAS_PATH);
    private static Skin UI_SKIN = new Skin(Gdx.files.internal(UI_SKIN_PATH), UI_TEXTUREATLAS);
    private String CHARACTER_SPRITE_PATH = "overworldentities/player/temp-character.png";
    private static Texture CHARACTER_TEXTURE; //= new Texture(CHARACTER_SPRITE_PATH);
    private String ENEMY_SPRITE_PATH;
    private Texture ENEMY_TEXTURE;
    private BattleManager battleManager;

    private final SongOfDeath game;
    private final Stage battleScreenStage;


    private static final int OPTIONS_DIALOG_WIDTH = 300;
    public BattleScreen(SongOfDeath game) {
        super(game);
        this.game = game;

        battleManager = new BattleManager(PartyMembers.activeParty);
        battleManager.addObserver(this);

        battleScreenStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(battleScreenStage);
        battleScreenTable = new Table();
        battleScreenTable.setFillParent(true);
        battleScreenStage.addActor(battleScreenTable);
        battleScreenStage.setDebugAll(true);

        trashLabel = new Label("", UI_SKIN);

        try{
            characterSprite = new Image(CHARACTER_TEXTURE);
        }catch (IllegalArgumentException e){
            CHARACTER_TEXTURE = new Texture("overworldentities/player/temp-character.png");
            characterSprite = new Image(CHARACTER_TEXTURE);
        }
        try{
            enemySprite = new Image(ENEMY_TEXTURE);
        }catch (IllegalArgumentException e){
            ENEMY_TEXTURE = new Texture("overworldentities/enemy/temp-enemy.png");
            enemySprite = new Image(ENEMY_TEXTURE);
        }
        battleScreenTable.add(characterSprite).center();
        battleScreenTable.add(enemySprite).center();
        battleScreenTable.row();
        battleScreenTable.add(trashLabel).height(75);
        battleScreenTable.row();

        optionsDialog = new Dialog("", UI_SKIN, "window-player");
        attackButton = new TextButton("Attack", UI_SKIN);
        attackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                Gdx.app.exit();
                battleManager.decisionMaking("ATTACK");
            }
        });
        battleScreenStage.addActor(attackButton);

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

        /*
        attackButton.setDisabled(true);
        skillButton.setDisabled(true);
        defendButton.setDisabled(true);
        itemButton.setDisabled(true);
        runButton.setDisabled(true);
         */

        battleScreenTable.add(optionsDialog).bottom();

        optionsDialog.getButtonTable().add(attackButton).width(100).height(50);
        optionsDialog.getButtonTable().row();
        optionsDialog.getButtonTable().add(skillButton);
        optionsDialog.getButtonTable().row();
        optionsDialog.getButtonTable().add(defendButton);
        optionsDialog.getButtonTable().row();
        optionsDialog.getButtonTable().add(itemButton);
        optionsDialog.getButtonTable().row();
        optionsDialog.getButtonTable().add(runButton);

        optionsDialog.setModal(false);
        optionsDialog.setTouchable(Touchable.enabled);

        infoDialog = new Dialog("", UI_SKIN, "window-player");
        characterInfo1 = new Label( "HP 50/50 MP 20/20", UI_SKIN);
        characterInfo2 = new Label("CharacterName HP 50/50 MP 20/20", UI_SKIN);
        characterInfo3 = new Label("CharacterName HP 50/50 MP 20/20", UI_SKIN);
        characterInfo4 = new Label("CharacterName HP 50/50 MP 20/20", UI_SKIN);
        battleScreenTable.add(infoDialog).width(400).bottom();

        infoDialog.getContentTable().add(characterInfo1).left();
        infoDialog.getContentTable().row();
        infoDialog.getContentTable().add(characterInfo2).left();
        infoDialog.getContentTable().row();
        infoDialog.getContentTable().add(characterInfo3).left();
        infoDialog.getContentTable().row();
        infoDialog.getContentTable().add(characterInfo4).left();
        infoDialog.getContentTable().row();


        battleManager.setBattleFinished(false);

    }

    @Override
    public void onNotify(Entity entity, BattleState state) {
        switch(state){
            case ENEMY_ADDED:
                ENEMY_SPRITE_PATH = entity.ENTITY_SPRITE_PATH;
                ENEMY_TEXTURE = new Texture(ENEMY_SPRITE_PATH);
                break;
            case PLAYER_ADDED:
                CHARACTER_SPRITE_PATH = entity.ENTITY_SPRITE_PATH;
                CHARACTER_TEXTURE = new Texture(CHARACTER_SPRITE_PATH);
                break;
            case PLAYER_TURN_START:
                attackButton.setDisabled(false);
                defendButton.setDisabled(false);
                runButton.setDisabled(false);
                System.out.println("Turn started!");
                break;
            case BATTLE_END:
                game.changeScreen(SongOfDeath.ScreenEnum.Overworld);
                break;
        }


    }


    @Override
    public void show() {
        battleScreenStage.addActor(battleScreenTable);
        Gdx.input.setInputProcessor(battleScreenStage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        battleScreenStage.act(delta);

        battleScreenStage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        battleScreenStage.dispose();
        battleScreenTable.remove();
    }

}
