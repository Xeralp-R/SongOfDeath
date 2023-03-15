package ph11.songofdeath.overworld;

import com.badlogic.gdx.Game;
import ph11.songofdeath.AbstractSongOfDeath;
import ph11.songofdeath.SongOfDeath;
import ph11.songofdeath.globalmanagers.GlobalResourceManager;

abstract public class AbstractSongOfDeathLevel implements AbstractSongOfDeath {
    SongOfDeath game;
    protected OverworldRepresentation playerRepresentation;
    public static final float MapUnit = 1/16f;
    public final static int SquareTileSize = 32;

    public AbstractSongOfDeathLevel(SongOfDeath game) {
        this.game = game;
    }

    public OverworldRepresentation getPlayerRepresentation() {
        return playerRepresentation;
    }
}
