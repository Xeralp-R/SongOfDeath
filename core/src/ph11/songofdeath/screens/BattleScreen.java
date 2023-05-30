package ph11.songofdeath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ph11.songofdeath.SongOfDeath;
import ph11.songofdeath.battle.internal.battle.BattleManager;
import ph11.songofdeath.battle.internal.battle.BattleObserver;
import ph11.songofdeath.battle.internal.entities.Entity;
import ph11.songofdeath.battle.internal.entities.characters.PartyMembers;
import ph11.songofdeath.customui.EntityButton;

public class BattleScreen extends AbstractScreen implements BattleObserver{
    private Table battleScreenTable;
    private Label trashLabel;
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
    private String enemyInfo, characterInfo;
    private Label enemyLabel, characterLabel;

    private static final int OPTIONS_DIALOG_WIDTH = 300;
    public BattleScreen(SongOfDeath game) {
        super(game);
        this.game = game;

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

        TextButton runButton = new TextButton("Run", UI_SKIN);
        runButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                battleManager.decisionMaking("RUN");
            }
        });
        runButton.setSize(20,50);
        battleScreenTable.add(runButton);

        TextButton defendButton = new TextButton("Guard", UI_SKIN);
        defendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                battleManager.decisionMaking("DEFEND");
            }
        });
        defendButton.setSize(20,50);
        battleScreenTable.add(defendButton);

        battleManager = new BattleManager(PartyMembers.activeParty);
        battleManager.addObserver(this);
        battleManager.initBattle(PartyMembers.activeParty);

        characterInfo = new String(battleManager.getActingPartyMember().getName() + " HP: " + battleManager.getActingPartyMember().getCurrentHP()
                + "/" + battleManager.getActingPartyMember().getTotalStats().getMaxHP());
        characterLabel = new Label(characterInfo, UI_SKIN);
        battleScreenTable.add(characterLabel);

        enemyInfo = new String(battleManager.getActingEnemy().getName() + " HP: " + battleManager.getActingEnemy().getCurrentHP() + "/" + battleManager.getActingEnemy().getTotalStats().getMaxHP());
        enemyLabel = new Label(enemyInfo, UI_SKIN);
        battleScreenTable.add(enemyLabel);
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

                TextureAtlas ENEMY_ATLAS = new TextureAtlas();
                ENEMY_ATLAS.addRegion("enemy_atlas", ENEMY_TEXTURE, 0, 0, ENEMY_TEXTURE.getWidth(), ENEMY_TEXTURE.getHeight());

                Skin ENEMY_SKIN = new Skin();
                ENEMY_SKIN.addRegions(ENEMY_ATLAS);
                try{
                    enemySprite = new EntityButton(entity, ENEMY_SKIN.getDrawable("enemy_atlas"));
                }catch (IllegalArgumentException e){
                    ENEMY_TEXTURE = new Texture("overworldentities/enemy/temp-enemy.png");
                    ENEMY_ATLAS.addRegion("enemy_atlas", ENEMY_TEXTURE, 0, 0, ENEMY_TEXTURE.getWidth(), ENEMY_TEXTURE.getHeight());

                    enemySprite = new EntityButton(entity, ENEMY_SKIN.getDrawable("enemy_atlas"));
                }

                enemySprite.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent even, float x, float y) {

                        System.out.println("Enemy selected!");
                    }
                });
                battleScreenTable.add(enemySprite);
                break;

            case PLAYER_ADDED:
                System.out.println("Adding Players!");
                CHARACTER_SPRITE_PATH = entity.ENTITY_SPRITE_PATH;
                CHARACTER_TEXTURE = new Texture(CHARACTER_SPRITE_PATH);

                TextureAtlas CHARACTER_ATLAS = new TextureAtlas();
                CHARACTER_ATLAS.addRegion("character_atlas", CHARACTER_TEXTURE, 0, 0, CHARACTER_TEXTURE.getWidth(), CHARACTER_TEXTURE.getHeight());

                Skin CHARACTER_SKIN = new Skin();
                CHARACTER_SKIN.addRegions(CHARACTER_ATLAS);
                try{
                    characterSprite = new EntityButton(entity, CHARACTER_SKIN.getDrawable("character_atlas"));
                }catch (IllegalArgumentException e){
                    CHARACTER_TEXTURE = new Texture("overworldentities/player/temp-character.png");
                    CHARACTER_ATLAS.addRegion("character_atlas", CHARACTER_TEXTURE, 0, 0, CHARACTER_TEXTURE.getWidth(), CHARACTER_TEXTURE.getHeight());

                    characterSprite = new EntityButton(entity, CHARACTER_SKIN.getDrawable("character_atlas"));
                }

                battleScreenTable.add(characterSprite);
                break;
            case TURN_DONE:
                characterInfo = new String(battleManager.getActingPartyMember().getName() + " HP: " + battleManager.getActingPartyMember().getCurrentHP() + "/" + battleManager.getActingPartyMember().getTotalStats().getMaxHP());
                enemyInfo = new String(battleManager.getActingEnemy().getName() + " HP: " + battleManager.getActingEnemy().getCurrentHP() + "/" + battleManager.getActingEnemy().getTotalStats().getMaxHP());
                characterLabel = new Label(characterInfo, UI_SKIN);
                enemyLabel = new Label(enemyInfo, UI_SKIN);
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
