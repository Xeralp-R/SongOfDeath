package ph11.songofdeath.overworld;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import ph11.songofdeath.AbstractSongOfDeath;
import ph11.songofdeath.SongOfDeath;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;

abstract public class AbstractSongOfDeathLevel implements AbstractSongOfDeath {
    SongOfDeath game;
    protected OverworldRepresentation playerRepresentation;
    public static final float UNIT_SCALE = 16f;
    public final static int SquareTileSize = 64;

    public AbstractSongOfDeathLevel(SongOfDeath game) {
        this.game = game;
    }

    public OverworldRepresentation getPlayerRepresentation() {
        return playerRepresentation;
    }
    abstract public Vector2 getPlayerStartPosition();
    abstract public MapLayer getPortalLayer();
    abstract public MapLayer getCollisionLayer();
    abstract public MapLayer getEntityLayer();
    abstract public Array<OverworldRepresentation> getEntities();
}
