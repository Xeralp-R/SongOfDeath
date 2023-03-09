package ph11.songofdeath.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ph11.songofdeath.SongOfDeath;

public class AbstractScreen implements Screen {
    protected final SongOfDeath game;
    protected OrthographicCamera defaultCamera;

    // viewport that keeps aspect ratios of the game when resizing
    protected Viewport viewport;
    // main stage of each screen
    protected Stage stage;

    public AbstractScreen(SongOfDeath game) {
        this.game = game;

        // the game will retain it's scaled dimensions regardless of resizing
        defaultCamera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, defaultCamera);
        stage = new Stage(viewport, game.getBatch());
    }

    public void createButton(String buttonText, float posX, float posY, Table table) {
        BitmapFont buttonFont = game.resourceManager.titleFont18;

        TextureRegionDrawable buttonNormalTexture = new TextureRegionDrawable(
                game.resourceManager.leDieuDeLaMerAtlas.findRegion("button")
        );
        buttonNormalTexture.tint(game.resourceManager.colorScheme.get("buttonNormalBackground"));

        TextureRegionDrawable buttonPressedTexture = new TextureRegionDrawable(
                game.resourceManager.leDieuDeLaMerAtlas.findRegion("button-pressed")
        );
        buttonPressedTexture.tint(game.resourceManager.colorScheme.get("buttonNormalBackground"));

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(
                buttonNormalTexture, buttonPressedTexture, null, buttonFont
        );
        TextButton button = new TextButton(buttonText, buttonStyle);
        button.getLabel().setColor(this.game.resourceManager.colorScheme.get("foreground"));

        table.add(button).padLeft(posX).padTop(posY);
        table.row();
    }

    public Table createTable() {
        Table table = new Table();
        table.setBounds(0,0, (float) stage.getWidth(), (float) stage.getHeight());
        return table;
    }

    @Override
    public void show() {
        // Nothing
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        // Nothing
    }

    @Override
    public void resume() {
        // Nothing
    }

    @Override
    public void hide() {
        // Nothing
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public OrthographicCamera getDefaultCamera() {
        return defaultCamera;
    }

    public Stage getStage() {
        return stage;
    }
}
