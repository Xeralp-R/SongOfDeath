package ph11.songofdeath.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

    public Table createTable() {
        Table table = new Table();
        table.setBounds(0,0, stage.getWidth(), stage.getHeight());
        return table;
    }

    public Label createLabel(String labelText, int fontSize, float padX, float padY, Table table) {
        // generate the font
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        BitmapFont labelFont = game.resourceManager.titleFontGenerator.generateFont(parameter);

        // generate the stylings for the font
        Label.LabelStyle style = new Label.LabelStyle(labelFont, game.resourceManager.colorScheme.get("foreground"));

        // generate the actual label
        Label label = new Label(labelText, style);

        table.add(label).padLeft(padX).padTop(padY);
        table.row();
        return label;
    }

    public TextButton createTextButton(String buttonText, float padX, float padY, Table table) {
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

        table.add(button).padLeft(padX).padTop(padY);
        table.row();
        return button;
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
