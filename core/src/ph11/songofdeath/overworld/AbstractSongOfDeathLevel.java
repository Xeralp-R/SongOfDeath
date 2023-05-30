package ph11.songofdeath.overworld;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import ph11.songofdeath.AbstractSongOfDeath;
import ph11.songofdeath.SongOfDeath;
import ph11.songofdeath.entity.overworldrepresentation.OverworldInteractable;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;
import ph11.songofdeath.entity.overworldrepresentation.ProcessorInterface;
import ph11.songofdeath.entity.overworldrepresentation.npcprocessors.NPCGraphicsProcessor;
import ph11.songofdeath.entity.overworldrepresentation.npcprocessors.NPCInputProcessor;
import ph11.songofdeath.entity.overworldrepresentation.npcprocessors.NPCPhysicsProcessor;
import ph11.songofdeath.screens.OverworldScreen;

abstract public class AbstractSongOfDeathLevel implements AbstractSongOfDeath {
    SongOfDeath game;
    protected OverworldRepresentation playerRepresentation;
    public static final float UNIT_SCALE = 1/32f;
    public final static int SquareTileSize = 64;

    //Map layers
    protected static final String COLLISION_LAYER = "COLLISION_LAYER";
    protected static final String ENTITY_LAYER = "ENTITY_LAYER";
    protected static final String PORTAL_LAYER = "PORTAL_LAYER";
    protected static final String INTERACTABLE_LAYER = "INTERACTABLE_LAYER";
    private final Json converter = new Json();
    /*
    protected static final String QUEST_ITEM_SPAWN_LAYER = "MAP_QUEST_ITEM_SPAWN_LAYER";
    protected static final String QUEST_DISCOVER_LAYER = "MAP_QUEST_DISCOVER_LAYER";
    protected static final String ENEMY_SPAWN_LAYER = "MAP_ENEMY_SPAWN_LAYER";
    protected static final String PARTICLE_EFFECT_SPAWN_LAYER = "PARTICLE_EFFECT_SPAWN_LAYER";*/

    public AbstractSongOfDeathLevel(SongOfDeath game) {
        this.game = game;
    }

    /**
     * Registers the npcs in the layer that you give it and returns their respective overworld
     * representations. In the mother project, this was done by an entity factory, which is the
     * correct way: however, due to lack of time, we can afford not to make it scalable at the moment
     * and only process 2 things: crows and slimes.
     * @param entityLayer The entity layer. NOTE: This function does not verify that the
     *                    layer passed is the actual entity layer, so be careful!
     * @return An array with all the needed overworld representations.
     */
    protected Array<OverworldRepresentation> registerNPCs(MapLayer entityLayer) {
        Array<OverworldRepresentation> npcs = new Array<>();

        for(MapObject object: entityLayer.getObjects()) {
            String objectName = object.getName();

            if (objectName == null || objectName.isEmpty() || objectName.equals("StartingPosition")) {
                continue;
            }

            // determine the picture that you're supposed to use
            // TODO: Make enemy-agnostic
            String npcImageFilepath = "overworldentities/enemy/temp-enemy.png";
            if (objectName.contains("Crow")) {
                npcImageFilepath = "overworldentities/enemy/crow_battle.png";
            }
            if (objectName.contains("Slime")) {
                npcImageFilepath = "overworldentities/enemy/slime_battle.png";
            }

            OverworldRepresentation newEntity = new OverworldRepresentation(
                    objectName,
                    npcImageFilepath,
                    new NPCInputProcessor(),
                    new NPCPhysicsProcessor(),
                    new NPCGraphicsProcessor()
            );

            //Get center of rectangle
            float x = ((RectangleMapObject)object).getRectangle().getX();
            float y = ((RectangleMapObject)object).getRectangle().getY();

            //scale by the unit to convert from map coordinates
            x *= UNIT_SCALE;
            y *= UNIT_SCALE;

            // inform the entity of its start position
            newEntity.sendMessage(ProcessorInterface.MessageType.INIT_START_POSITION, converter.toJson(new Vector2(x, y)));

            // IMPORTANT! Registers it also an interactable
            // TODO: Make it enemy-agnostic
            String dialog = "...!";
            newEntity.registerInteractable(dialog, OverworldInteractable.InteractionResult.Battle);

            npcs.add(newEntity);
        }
        return npcs;
    }

    public OverworldRepresentation getPlayerRepresentation() {
        return playerRepresentation;
    }
    abstract public Vector2 getPlayerStartPosition();
    abstract public Vector2 getPlayerStartPositionScaled();
    abstract public MapLayer getPortalLayer();
    abstract public MapLayer getCollisionLayer();
    abstract public MapLayer getEntityLayer();
    abstract public Array<OverworldRepresentation> getEntities();
    abstract public Array<OverworldInteractable> getInteractables();
    abstract public void renderEntities(OverworldScreen screen, float delta);
    abstract public void pause();
    abstract public void connectPlayerMovement();
    abstract public void disconnectPlayerMovement();
}
