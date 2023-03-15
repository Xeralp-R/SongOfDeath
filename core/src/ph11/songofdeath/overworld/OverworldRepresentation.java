package ph11.songofdeath.overworld;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class OverworldRepresentation {
    public final Texture image;
    public final AssetManager assetManager = new AssetManager();

    public OverworldRepresentation(String pathToImage) {
        assetManager.load(pathToImage, Texture.class);

        assetManager.finishLoading();

        image = assetManager.get(pathToImage, Texture.class);
    }
    public OverworldRepresentation(Texture image) {
        this.image = image;
    }
}
