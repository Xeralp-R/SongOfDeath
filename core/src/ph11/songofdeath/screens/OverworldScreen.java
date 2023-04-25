package ph11.songofdeath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
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
import ph11.songofdeath.entity.overworldrepresentation.ProcessorInterface;
import ph11.songofdeath.overworld.AbstractSongOfDeathLevel;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;

public class OverworldScreen extends AbstractScreen {
    public static class VIEWPORT {
        private static float viewportWidth;
        private static float viewportHeight;
        private static float virtualWidth;
        private static float virtualHeight;
        private static float physicalWidth;
        private static float physicalHeight;
        private static float aspectRatio;
    }

    private static GameState gameState;
    protected OrthogonalTiledMapRenderer mapRenderer = null;
    //protected MapManager mapManager;
    protected TiledMap tiledMap;
    protected OrthographicCamera camera;
    private Json json;
    private AbstractSongOfDeathLevel level;
    private InputMultiplexer inputMultiplexer;
    private OverworldRepresentation player;
    private float startX;
    private float startY;
    private float levelWidth;
    private float levelHeight;
    private float endX;
    private float endY;

    private final Stage overworldStage;
    public RectangleMapObject playerBox;
    boolean firstLoad;

    public OverworldScreen(AbstractSongOfDeathLevel level, TiledMap map) {
        super(level);
        this.level = level;
        json = new Json();
        this.tiledMap = map;

        overworldStage = new Stage(super.viewport, level.getBatch());

        setGameState(GameState.RUNNING);
        setupViewport(15, 15);

        // for now...
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT.viewportWidth, VIEWPORT.viewportHeight);

        // the old way had a way to get it out of a save state
        // for now, we're just going to use a straight one
        player = level.getPlayerRepresentation();
        //createImage(player.image, 1200, 50, overworldTable);

        this.inputMultiplexer = new InputMultiplexer();
        this.inputMultiplexer.addProcessor(overworldStage);
        this.inputMultiplexer.addProcessor(player.getInputProcessor());
        Gdx.input.setInputProcessor(this.inputMultiplexer);
        this.firstLoad = true;
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
        Gdx.input.setInputProcessor(this.inputMultiplexer);

        if (mapRenderer == null) {
            mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, AbstractSongOfDeathLevel.UNIT_SCALE);
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


        if (firstLoad) {
            //mapRenderer.setMap(mapManager.getCurrentTiledMap());
            player.sendMessage(ProcessorInterface.MessageType.INIT_START_POSITION, json.toJson(level.getPlayerStartPositionScaled()));

            camera.position.set(level.getPlayerStartPosition(), 0f);
            camera.update();

            //playerHUD.updateEntityObservers();

            this.firstLoad = false;
        }
        mapRenderer.render();
        level.renderEntities(this, delta);
        //mapManager.updateCurrentMapEntities(mapManager, mapRenderer.getBatch(), delta);
        //player.update(mapManager, mapRenderer.getBatch(), delta);

        startX = camera.viewportWidth / 2;
        startY = camera.viewportHeight / 2;

        levelWidth = tiledMap.getProperties().get("width", Integer.class);
        levelHeight = tiledMap.getProperties().get("height", Integer.class);

        endX = levelWidth * AbstractSongOfDeathLevel.SquareTileSize * AbstractSongOfDeathLevel.UNIT_SCALE - startX * 2;
        endY = levelHeight * AbstractSongOfDeathLevel.SquareTileSize * AbstractSongOfDeathLevel.UNIT_SCALE - startY * 2;
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

    private static void setupViewport(int width, int height) {
        //Make the viewport a percentage of the total display area
        VIEWPORT.virtualWidth = width;
        VIEWPORT.virtualHeight = height;

        //Current viewport dimensions
        VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
        VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;

        //pixel dimensions of display
        VIEWPORT.physicalWidth = Gdx.graphics.getWidth();
        VIEWPORT.physicalHeight = Gdx.graphics.getHeight();

        //aspect ratio for current viewport
        VIEWPORT.aspectRatio = (VIEWPORT.virtualWidth / VIEWPORT.virtualHeight);

        //update viewport if there could be skewing
        if (VIEWPORT.physicalWidth / VIEWPORT.physicalHeight >= VIEWPORT.aspectRatio) {
            //Letterbox left and right
            VIEWPORT.viewportWidth = VIEWPORT.viewportHeight * (VIEWPORT.physicalWidth/VIEWPORT.physicalHeight);
            VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;
        } else {
            //letterbox above and below
            VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
            VIEWPORT.viewportHeight = VIEWPORT.viewportWidth * (VIEWPORT.physicalHeight/VIEWPORT.physicalWidth);
        }

        //LOGGER.debug("WorldRenderer: virtual: ({},{})", VIEWPORT.virtualWidth, VIEWPORT.virtualHeight);
        //LOGGER.debug("WorldRenderer: viewport: ({},{})", VIEWPORT.viewportWidth, VIEWPORT.viewportHeight);
        //LOGGER.debug("WorldRenderer: physical: ({},{})", VIEWPORT.physicalWidth, VIEWPORT.physicalHeight);
    }

    public AbstractSongOfDeathLevel getLevel() {
        return this.level;
    }
    public Batch getBatch() { return mapRenderer.getBatch(); }
    public OrthographicCamera getCamera() { return this.camera; }
}
