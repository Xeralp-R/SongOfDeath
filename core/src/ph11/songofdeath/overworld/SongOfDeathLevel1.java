package ph11.songofdeath.overworld;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
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
    private boolean playerMovementConnected = true;

    // layers!
    protected MapLayer collisionLayer = null;
    protected MapLayer portalLayer = null;
    protected MapLayer entityLayer = null;
    protected MapLayer interactableLayer = null;

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
        this.level1 = new TmxMapLoader().load("maps/Level1_v2.tmx");
        collisionLayer = level1.getLayers().get(COLLISION_LAYER);
        portalLayer = level1.getLayers().get(PORTAL_LAYER);
        entityLayer = level1.getLayers().get(ENTITY_LAYER);
        interactableLayer = level1.getLayers().get(INTERACTABLE_LAYER);

        // set the starting position
        ((RectangleMapObject)entityLayer.getObjects().get("StartingPosition")).getRectangle().getPosition(this.playerStartPosition);

        //this.level1 = assetManager.get("maps/Level1.tmx");
        this.playerRepresentation = new OverworldRepresentation(
                "player",
                "overworldentities/player/temp-character.png",
                new PlayerInputProcessor(),
                new PlayerPhysicsProcessor(),
                new PlayerGraphicsProcessor()
        );
        this.entities.add(playerRepresentation);

        Array<OverworldRepresentation> npcs = super.registerNPCs(this.entityLayer);
        this.entities.addAll(npcs);

        // get default interactables
        for (MapObject mapObject : interactableLayer.getObjects()) {

            // TODO: Make object-agnostic
            RectangleMapObject convertedObject = (RectangleMapObject) mapObject;
            if (mapObject.getName().equals("Plinth")) {
                String dialogText = "It seems to be a very old plaque, but it's somehow still legible. It reads:|\"Blessed are those who enter here,\nFor eternal paradise is their reward.\"|It doesn't seem like anyone's been here in some time...";
                this.interactables.add(new OverworldInteractable(convertedObject.getRectangle(), dialogText, OverworldInteractable.InteractionResult.NoResult));
            }
        }

        this.batch = new SpriteBatch();
        this.resourceManager = GlobalResourceManager.get();

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
    public Array<OverworldInteractable> getInteractables() {
        Array<OverworldInteractable> tempInteractables = new Array<>();

        tempInteractables.addAll(this.interactables);
        for (OverworldRepresentation entity : this.entities) {
            tempInteractables.add(entity.getInteractable());
        }

        return tempInteractables;
    }
    @Override
    public void renderEntities(OverworldScreen screen, float delta) {
        if (!this.playerMovementConnected) {
            return;
        }
        this.playerRepresentation.render(screen, delta);
    }

    @Override
    public void pause() {
        game.changeScreen(SongOfDeath.ScreenEnum.Pause);
    }

    @Override
    public void connectPlayerMovement() {
        this.playerMovementConnected = true;
    }

    @Override
    public void disconnectPlayerMovement() {
        this.playerMovementConnected = false;
    }
}