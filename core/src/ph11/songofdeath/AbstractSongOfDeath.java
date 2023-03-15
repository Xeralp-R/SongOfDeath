package ph11.songofdeath;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ph11.songofdeath.globalmanagers.GlobalResourceManager;

abstract public interface AbstractSongOfDeath {
    public GlobalResourceManager getResourceManager();
    public SpriteBatch getBatch();
}
