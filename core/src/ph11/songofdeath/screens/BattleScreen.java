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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ph11.songofdeath.SongOfDeath;
import ph11.songofdeath.battle.internal.battle.BattleManager;
import ph11.songofdeath.battle.internal.battle.BattleObserver;
import ph11.songofdeath.battle.internal.entities.Entity;
import ph11.songofdeath.battle.internal.entities.characters.PartyMembers;
import ph11.songofdeath.customui.ButtonWindow;
import ph11.songofdeath.customui.EntityButton;

public class BattleScreen extends AbstractScreen implements BattleObserver{
    private Table battleScreenTable;
    private Label trashLabel;
    private Label characterInfo1;
    private Label characterInfo2;
    private Label characterInfo3;
    private Label characterInfo4;

    private ButtonWindow battleWindow;
    private Dialog infoDialog;
    private EntityButton characterSprite;
    private EntityButton enemySprite;

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
        TextButton attackButton = new TextButton("Attack", UI_SKIN);
        attackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                battleManager.decisionMaking("ATTACK");
            }
        });
        attackButton.setSize(20,50);
        battleScreenTable.add(attackButton);

    }

    @Override
    public void onNotify(Entity entity, BattleState state) {
        switch(state){
            case ENEMY_ADDED:
                //Everytime an enemy gets added to the screen a new EntityButton is created for that enemy
                //TODO: Might be able to turn this into a function
                System.out.println("Adding Enemies!");
                ENEMY_SPRITE_PATH = entity.ENTITY_SPRITE_PATH;
                ENEMY_TEXTURE = new Texture(ENEMY_SPRITE_PATH);

                try{
                    enemySprite = new EntityButton(entity, (Drawable) ENEMY_TEXTURE);
                }catch (IllegalArgumentException e){
                    ENEMY_TEXTURE = new Texture("overworldentities/enemy/temp-enemy.png");
                    enemySprite = new EntityButton(entity, (Drawable) ENEMY_TEXTURE);
                }

                enemySprite.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent even, float x, float y) {
                        battleManager.selectTarget(enemySprite.associatedEntity);
                    }
                });
                battleScreenTable.add(enemySprite);
                break;
            case PLAYER_ADDED:
                System.out.println("Adding Players!");
                CHARACTER_SPRITE_PATH = entity.ENTITY_SPRITE_PATH;
                CHARACTER_TEXTURE = new Texture(CHARACTER_SPRITE_PATH);

                try{
                    characterSprite = new EntityButton(entity, (Drawable) CHARACTER_TEXTURE);
                }catch (IllegalArgumentException e){
                    CHARACTER_TEXTURE = new Texture("overworldentities/player/temp-character.png");
                    characterSprite = new EntityButton(entity, (Drawable) CHARACTER_TEXTURE);
                }

                battleScreenTable.add(characterSprite);
                break;
            case BATTLE_END:
                game.changeScreen(SongOfDeath.ScreenEnum.Overworld);
                break;
            case SELECT_TARGET:
                enemySprite.setDisabled(false);
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
