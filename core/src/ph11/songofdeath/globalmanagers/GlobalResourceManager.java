package ph11.songofdeath.globalmanagers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ObjectMap;

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
    public final FreeTypeFontGenerator titleFontGenerator;

    // Color!
    public final ObjectMap<String, Color> colorScheme;

    private static final AssetManager assetManager = new AssetManager();

    private GlobalResourceManager() {
        // FONT
        this.titleFontGenerator = new FreeTypeFontGenerator(new FileHandle("fonts/AncientModernTales.ttf"));

        // Color
        colorScheme = new ObjectMap<>();
        colorScheme.put("foreground", new Color(1, 1, 1, 1));
        colorScheme.put("background", new Color(1, 0, 0, 0));
        colorScheme.put("highlightBackground", new Color(1, 0.25F, 0.25F, 0.25F));
    }

    public void dispose() {
        assetManager.dispose();

        titleFontGenerator.dispose();
    }
}
