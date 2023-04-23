package ph11.songofdeath.overworld;

import com.badlogic.gdx.math.Vector2;
import ph11.songofdeath.AbstractSongOfDeath;
import ph11.songofdeath.SongOfDeath;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;

abstract public class AbstractSongOfDeathLevel implements AbstractSongOfDeath {
    SongOfDeath game;
    protected OverworldRepresentation playerRepresentation;
    public static final float MapUnit = 1f;
    public final static int SquareTileSize = 64;

    public AbstractSongOfDeathLevel(SongOfDeath game) {
        this.game = game;
    }

    public OverworldRepresentation getPlayerRepresentation() {
        return playerRepresentation;
    }
    abstract public Vector2 getPlayerStartPosition();
}
