package ph11.songofdeath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector3;

/*
import ph11.songofdeath.SongOfDeath;
import com.gdx.game.audio.AudioManager;
import com.gdx.game.audio.AudioObserver;
import com.gdx.game.camera.CameraStyles;
import com.gdx.game.component.Component;
import com.gdx.game.component.ComponentObserver;
import com.gdx.game.entities.Entity;
import com.gdx.game.entities.EntityFactory;
import com.gdx.game.entities.player.PlayerHUD;
import com.gdx.game.entities.player.PlayerInputComponent;
import com.gdx.game.manager.ResourceManager;
import com.gdx.game.map.Map;
import com.gdx.game.map.MapFactory;
import com.gdx.game.map.MapManager;
import com.gdx.game.profile.ProfileManager;*/
import ph11.songofdeath.overworld.AbstractSongOfDeathLevel;
import ph11.songofdeath.overworld.OverworldRepresentation;

import java.util.ArrayList;

public class OverworldScreen extends AbstractScreen {

    private static GameState gameState;
    protected OrthogonalTiledMapRenderer mapRenderer = null;
    //protected MapManager mapManager;
    protected TiledMap tiledMap;
    protected OrthographicCamera camera;
    private Json json;
    private AbstractSongOfDeathLevel game;
    private InputMultiplexer multiplexer;
    private OverworldRepresentation player;
    private float startX;
    private float startY;
    private float levelWidth;
    private float levelHeight;
    private float endX;
    private float endY;

    private final Stage overworldStage;

    public OverworldScreen(AbstractSongOfDeathLevel game, TiledMap map) {
        super(game);
        this.game = game;
        json = new Json();
        this.tiledMap = map;

        overworldStage = new Stage(super.viewport, game.getBatch());

        setGameState(GameState.RUNNING);

        // for now...
        camera = super.defaultCamera;

        // the old way had a way to get it out of a save state
        // for now, we're just going to use a straight one
        player = game.getPlayerRepresentation();

        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(overworldStage);
    }

    public static GameState getGameState() {
        return gameState;
    }

    public static void setGameState(GameState state) {
        switch (state) {
            case RUNNING:
                gameState = GameState.RUNNING;
                break;
                /*
            case LOADING:
                ProfileManager.getInstance().loadProfile();
                gameState = GameState.RUNNING;
                break;
            case SAVING:
                ProfileManager.getInstance().saveProfile();
                gameState = GameState.PAUSED;
                break;*/
            case PAUSED:
                if (gameState == GameState.PAUSED) {
                    gameState = GameState.RUNNING;
                } else if (gameState == GameState.RUNNING) {
                    gameState = GameState.PAUSED;
                }
                break;
            case GAME_OVER:
                gameState = GameState.GAME_OVER;
                break;
            default:
                gameState = GameState.RUNNING;
                break;
        }

    }

    @Override
    public void show() {
        setGameState(GameState.LOADING);
        Gdx.input.setInputProcessor(overworldStage);

        if (mapRenderer == null) {
            mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, AbstractSongOfDeathLevel.MapUnit);
        }
    }

    @Override
    public void hide() {
        if (gameState != GameState.GAME_OVER) {
            setGameState(GameState.SAVING);
        }

        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {/*
        if (gameState == GameState.PAUSED) {
            player.updateInput(delta);
            playerHUD.render(delta);
            return;
        }*/
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(camera);

        mapRenderer.getBatch().enableBlending();
        mapRenderer.getBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        if (delta == 0) {
            //mapRenderer.setMap(mapManager.getCurrentTiledMap());
            //player.sendMessage(Component.MESSAGE.INIT_START_POSITION, json.toJson(mapManager.getPlayerStartUnitScaled()));

            camera.position.set(0, 0, 0f);
            camera.update();

            //playerHUD.updateEntityObservers();

            //mapManager.setMapChanged(false);
        }

        mapRenderer.render();
        //mapManager.updateCurrentMapEntities(mapManager, mapRenderer.getBatch(), delta);
        //player.update(mapManager, mapRenderer.getBatch(), delta);

        startX = camera.viewportWidth / 2;
        startY = camera.viewportHeight / 2;

        levelWidth = tiledMap.getProperties().get("width", Integer.class);
        levelHeight = tiledMap.getProperties().get("height", Integer.class);

        endX = levelWidth * AbstractSongOfDeathLevel.SquareTileSize * AbstractSongOfDeathLevel.MapUnit - startX * 2;
        endY = levelHeight * AbstractSongOfDeathLevel.SquareTileSize * AbstractSongOfDeathLevel.MapUnit - startY * 2;
        this.boundaries(camera, startX, startY, endX, endY);

        //playerHUD.render(delta);

        //musicTheme = MapFactory.getMapTable().get(mapManager.getCurrentMapType()).getMusicTheme();
        //AudioManager.getInstance().setCurrentMusic(ResourceManager.getMusicAsset(musicTheme.getValue()));
    }

    public void boundaries(OrthographicCamera camera, float startX, float startY, float width, float height) {
        Vector3 position = camera.position;

        if(position.x < startX) {
            position.x = startX;
        }
        if(position.y < startY) {
            position.y = startY;
        }

        if(position.x > startX + width) {
            position.x = startX + width;
        }
        if(position.y > startY + height) {
            position.y = startY + height;
        }

        camera.position.set(position);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        //camera.setToOrtho(false, VIEWPORT.viewportWidth, VIEWPORT.viewportHeight);
        //playerHUD.resize((int) VIEWPORT.physicalWidth, (int) VIEWPORT.physicalHeight);
    }

    @Override
    public void pause() {
        setGameState(GameState.SAVING);
        //playerHUD.pause();
    }

    @Override
    public void resume() {
        setGameState(GameState.LOADING);
        //playerHUD.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
        /*
        if (player != null) {
            player.unregisterObservers();
            player.dispose();
        }*/

        if (mapRenderer != null) {
            mapRenderer.dispose();
        }
    }

    public enum GameState {
        SAVING,
        LOADING,
        RUNNING,
        PAUSED,
        GAME_OVER
    }
}
