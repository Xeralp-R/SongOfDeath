package ph11.songofdeath.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ph11.songofdeath.AbstractSongOfDeath;

public class AbstractScreen implements Screen {
    protected final AbstractSongOfDeath game;
    protected OrthographicCamera defaultCamera;

    // viewport that keeps aspect ratios of the game when resizing
    protected Viewport viewport;
    // main stage of each screen
    protected Stage stage;

    public AbstractScreen(AbstractSongOfDeath game) {
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

    public Image createImage(Texture imageProper, float padX, float padY, Table table) {
        Image image = new Image(imageProper);
        image.setSize(64, 64);
        table.add(image).padLeft(padX).padTop(padY);
        table.row();
        return image;
    }

    public Label createLabel(String labelText, int fontSize, float padX, float padY, Table table) {
        // generate the font
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        BitmapFont labelFont = game.getResourceManager().titleFontGenerator.generateFont(parameter);

        // generate the stylings for the font
        Label.LabelStyle style = new Label.LabelStyle(labelFont, game.getResourceManager().colorScheme.get("foreground"));

        // generate the actual label
        Label label = new Label(labelText, style);

        table.add(label).padLeft(padX).padTop(padY);
        table.row();
        return label;
    }

    public TextButton createTextButton(String buttonText, float padX, float padY, Table table) {
        BitmapFont buttonFont = game.getResourceManager().bodyFontSized;

        Drawable buttonNormalTexture = game.getResourceManager().leDieuDeLaMerBareSkin.newDrawable("button");
        Drawable buttonPressedTexture = game.getResourceManager().leDieuDeLaMerBareSkin.newDrawable("button-pressed");
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(
                buttonNormalTexture, buttonPressedTexture, null, buttonFont
        );

        TextButton button = new TextButton(buttonText, buttonStyle);
        button.getLabel().setColor(this.game.getResourceManager().colorScheme.get("foreground"));

        table.add(button).padLeft(padX).padTop(padY);
        table.row();
        return button;
    }

    public TextButton createTextButton(String buttonText, float width, float height, float padX, float padY, Table table) {
        BitmapFont buttonFont = game.getResourceManager().bodyFontSized;

        Drawable buttonNormalTexture = game.getResourceManager().leDieuDeLaMerBareSkin.newDrawable("button");
        Drawable buttonPressedTexture = game.getResourceManager().leDieuDeLaMerBareSkin.newDrawable("button-pressed");
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(
                buttonNormalTexture, buttonPressedTexture, null, buttonFont
        );

        TextButton button = new TextButton(buttonText, buttonStyle);
        button.setSize(width, height);
        button.getLabel().setColor(this.game.getResourceManager().colorScheme.get("foreground"));

        table.add(button).width(width).height(height).padLeft(padX).padTop(padY);
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
