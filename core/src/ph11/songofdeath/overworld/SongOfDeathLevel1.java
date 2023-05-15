package ph11.songofdeath.overworld;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import ph11.songofdeath.SongOfDeath;
import ph11.songofdeath.entity.overworldrepresentation.OverworldInteractable;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;
import ph11.songofdeath.entity.overworldrepresentation.playerprocessors.PlayerGraphicsProcessor;
import ph11.songofdeath.entity.overworldrepresentation.playerprocessors.PlayerInputProcessor;
import ph11.songofdeath.entity.overworldrepresentation.playerprocessors.PlayerPhysicsProcessor;
import ph11.songofdeath.globalmanagers.GlobalResourceManager;
import ph11.songofdeath.screens.OverworldScreen;

public class SongOfDeathLevel1 extends AbstractSongOfDeathLevel {
    private GlobalResourceManager resourceManager;
    private SpriteBatch batch;
    private static final AssetManager assetManager = new AssetManager();
    public final TiledMap level1;

    // layers!
    protected MapLayer collisionLayer = null;
    protected MapLayer portalLayer = null;
    protected MapLayer entityLayer = null;

    // TODO: GET IT OUT OF THE MAIN CLASS!
    private OverworldScreen overworldScreen;
    private Array<OverworldRepresentation> entities = new Array<>();
    private Array<OverworldInteractable> interactables = new Array<>();
    private Vector2 playerStartPosition = new Vector2(2085, 1495);

    public SongOfDeathLevel1(SongOfDeath game) {
        super(game);

        // load the maps that we need
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load("overworldentities/player/temp-character.png", Texture.class);
        //assetManager.load("maps/Level1.tmx", TiledMap.class);
        assetManager.finishLoading();

        // load the map and layers
        this.level1 = new TmxMapLoader().load("maps/Level1_v1.tmx");
        collisionLayer = level1.getLayers().get(COLLISION_LAYER);
        portalLayer = level1.getLayers().get(PORTAL_LAYER);
        entityLayer = level1.getLayers().get(ENTITY_LAYER);

        // set the starting position
        ((RectangleMapObject)entityLayer.getObjects().get(0)).getRectangle().getPosition(this.playerStartPosition);

        //this.level1 = assetManager.get("maps/Level1.tmx");
        this.playerRepresentation = new OverworldRepresentation(new PlayerInputProcessor(), new PlayerPhysicsProcessor(), new PlayerGraphicsProcessor());
        this.entities.add(playerRepresentation);
        this.batch = new SpriteBatch();
        this.resourceManager = GlobalResourceManager.get();

        // TODO: Add interactable making!!!!

        // make and show the screen
        overworldScreen = new OverworldScreen(this, level1);
        game.setScreen(overworldScreen);
    }

    @Override
    public GlobalResourceManager getResourceManager() {
        return resourceManager;
    }

    @Override
    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public Vector2 getPlayerStartPosition() {
        return playerStartPosition;
    }

    @Override
    public Vector2 getPlayerStartPositionScaled() {
        Vector2 playerStart = this.playerStartPosition.cpy();
        playerStart.set(this.playerStartPosition.x * UNIT_SCALE, this.playerStartPosition.y * UNIT_SCALE);
        return playerStart;
    }

    @Override
    public MapLayer getPortalLayer() {
        return this.portalLayer;
    }

    @Override
    public MapLayer getCollisionLayer() {
        return this.collisionLayer;
    }

    @Override
    public MapLayer getEntityLayer() {
        return this.entityLayer;
    }

    @Override
    public Array<OverworldRepresentation> getEntities() {
        return entities;
    }

    @Override
    public Array<OverworldInteractable> getInteractables() { return interactables; }
    @Override
    public void renderEntities(OverworldScreen screen, float delta) {
        this.playerRepresentation.render(screen, delta);
    }

    @Override
    public void pause() {
        game.changeScreen(SongOfDeath.ScreenEnum.Pause);
    }
}