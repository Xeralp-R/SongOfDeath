package ph11.songofdeath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ph11.songofdeath.SongOfDeath;

public class LoadGameScreen extends AbstractScreen {
    private final Table savesTable;
    private final Stage savesStage;
    private Actor backButton;

    public LoadGameScreen(final SongOfDeath game) {
        super(game);

        savesStage = new Stage(super.viewport, game.getBatch());
        savesTable = createTable();

        createLabel("Saves", 120,0,0,savesTable,true);
        createTextButton("Work in Progress", 1000,120,0,30,savesTable,true);
        createTextButton("Work in Progress", 1000,120,0,0,savesTable,true);
        createTextButton("Work in Progress", 1000,120,0,0,savesTable,true);
        backButton = createTextButton("Back",-900,0,savesTable,true);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                game.changeScreen(SongOfDeath.ScreenEnum.MainMenu);
            }
        });
    }
    @Override
    public void show() {
        savesStage.addActor(savesTable);
        Gdx.input.setInputProcessor(savesStage);
        //Gdx.graphics.setCursor(Gdx.graphics.newCursor(game.resourceManager.cursor, 0, 0));
    }

    @Override
    public void render(float delta) {
        //stateTime += Gdx.graphics.getDeltaTime();
        //TextureRegion currentFrame = flowAnimation.getKeyFrame(stateTime, true);

        // the background itself
        super.render(delta);
        game.getBatch().begin();
        game.getBatch().draw(game.getResourceManager().background, 0,0, 1280, 720);
        game.getBatch().end();

        // for the stuff atop the background
        savesStage.act(delta);
        savesStage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        savesTable.remove();
    }
}
