package ph11.songofdeath.overworld;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import ph11.songofdeath.SongOfDeath;
import ph11.songofdeath.globalmanagers.GlobalResourceManager;
import ph11.songofdeath.screens.OverworldScreen;

public class SongOfDeathLevel1 extends AbstractSongOfDeathLevel {
    private GlobalResourceManager resourceManager;
    private SpriteBatch batch;
    private static final AssetManager assetManager = new AssetManager();
    public final TiledMap level1;
    // TODO: GET IT OUT OF THE MAIN CLASS!
    private OverworldScreen overworldScreen;

    public SongOfDeathLevel1(SongOfDeath game) {
        super(game);

        // load the maps that we need
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        assetManager.load("overworldentities/player/temp-character.png", Texture.class);
        //assetManager.load("maps/Level1.tmx", TiledMap.class);

        assetManager.finishLoading();

        //this.level1 = assetManager.get("maps/Level1.tmx");
        this.playerRepresentation = new OverworldRepresentation(assetManager.<Texture>get("overworldentities/player/temp-character.png"));

        this.level1 = new TmxMapLoader().load("maps/Level1.tmx");
        batch = new SpriteBatch();
        this.resourceManager = GlobalResourceManager.get();

        overworldScreen = new OverworldScreen(this, level1);

        game.setScreen(overworldScreen);
    }

    @Override
    public GlobalResourceManager getResourceManager() {
        return resourceManager;
    }

    @Override
    public SpriteBatch getBatch() {
        return batch;
    }
}
