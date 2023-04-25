package ph11.songofdeath.overworld;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import ph11.songofdeath.AbstractSongOfDeath;
import ph11.songofdeath.SongOfDeath;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;
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
    /*
    protected static final String QUEST_ITEM_SPAWN_LAYER = "MAP_QUEST_ITEM_SPAWN_LAYER";
    protected static final String QUEST_DISCOVER_LAYER = "MAP_QUEST_DISCOVER_LAYER";
    protected static final String ENEMY_SPAWN_LAYER = "MAP_ENEMY_SPAWN_LAYER";
    protected static final String PARTICLE_EFFECT_SPAWN_LAYER = "PARTICLE_EFFECT_SPAWN_LAYER";*/

    public AbstractSongOfDeathLevel(SongOfDeath game) {
        this.game = game;
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
    abstract public void renderEntities(OverworldScreen screen, float delta);
}
