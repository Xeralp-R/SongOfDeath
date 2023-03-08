package ph11.songofdeath.globalmanagers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GlobalResourceManager {
    private static GlobalResourceManager instance;

    public static GlobalResourceManager get() {
        if (instance == null) {
            synchronized (GlobalResourceManager.class) {
                if (instance == null) {
                    instance = new GlobalResourceManager();
                }
            }
        }
        return instance;
    }

    // IMAGES
    public Texture background;

    // FONT
    public final BitmapFont titleFont;

    // Color!
    public Colors colorScheme;

    private static final AssetManager assetManager = new AssetManager();

    private GlobalResourceManager() {
        // FONT
        titleFont = new BitmapFont(Gdx.files.internal("fonts/pixel.fnt"), atlas.findRegion("pixel"), false);
    }

    public void dispose() {
        assetManager.dispose();

        titleFont.dispose();
    }
}
