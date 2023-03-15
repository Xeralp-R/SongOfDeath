package ph11.songofdeath.globalmanagers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import ph11.songofdeath.overworld.OverworldRepresentation;

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

    // Atlases!
    public final TextureAtlas laMereDeLaTerreAtlas;
    public final TextureAtlas leDieuDeLaMerAtlas;
    public final Skin laMereDeLaTerreBareSkin;
    public final Skin leDieuDeLaMerBareSkin;

    // IMAGES
    // image from https://imgur.com/9dn0cNv
    public Texture background;

    // FONT
    public final FreeTypeFontGenerator titleFontGenerator;
    public final BitmapFont titleFontSized;

    public final FreeTypeFontGenerator bodyFontGenerator;
    public final BitmapFont bodyFontSized;

    // Color!
    public final ObjectMap<String, Color> colorScheme;

    private static final AssetManager assetManager = new AssetManager();

    private GlobalResourceManager() {
        // Atlas!
        assetManager.load("pixthulhu-ui/pixthulhu-ui.atlas", TextureAtlas.class);
        assetManager.load("terra-mother-ui/terra-mother-ui.atlas", TextureAtlas.class);

        // that one image!
        assetManager.load("images/graymountain.png", Texture.class);

        assetManager.finishLoading();

        leDieuDeLaMerAtlas = assetManager.get("pixthulhu-ui/pixthulhu-ui.atlas", TextureAtlas.class);
        laMereDeLaTerreAtlas = assetManager.get("terra-mother-ui/terra-mother-ui.atlas", TextureAtlas.class);

        this.background = assetManager.get("images/graymountain.png", Texture.class);

        leDieuDeLaMerBareSkin = new Skin();
        leDieuDeLaMerBareSkin.addRegions(leDieuDeLaMerAtlas);
        laMereDeLaTerreBareSkin = new Skin();
        laMereDeLaTerreBareSkin.addRegions(laMereDeLaTerreAtlas);

        // FONT
        this.titleFontGenerator = new FreeTypeFontGenerator(new FileHandle("fonts/AncientModernTales.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 108;
        this.titleFontSized = this.titleFontGenerator.generateFont(parameter);

        this.bodyFontGenerator = new FreeTypeFontGenerator(new FileHandle("fonts/PixeloidSans.ttf"));
        // just reusing the same paramter
        parameter.size = 32;
        this.bodyFontSized = this.bodyFontGenerator.generateFont(parameter);

        // Color
        colorScheme = new ObjectMap<>();
        colorScheme.put("foreground", new Color(1, 1, 1, 1));
        colorScheme.put("background", new Color(1, 0, 0, 0));
        colorScheme.put("buttonNormalBackground", new Color(0.25F, 0.25F, 0.25F, 1));
        colorScheme.put("buttonPressedBackground", new Color(0.15F, 0.15F, 0.15F, 1));
    }

    public void dispose() {
        assetManager.dispose();

        laMereDeLaTerreAtlas.dispose();
        leDieuDeLaMerAtlas.dispose();

        titleFontGenerator.dispose();
    }
}
