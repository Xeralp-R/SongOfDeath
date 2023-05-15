package ph11.songofdeath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ph11.songofdeath.SongOfDeath;

public class PauseScreen extends AbstractScreen{
    private final Stage PauseStage;
    private final Table PauseTable;
    private Actor resume, loads, options, mainmenu;
    public PauseScreen(final SongOfDeath game) {
        super(game);

        PauseStage = new Stage(super.viewport, game.getBatch());
        PauseTable = createTable();

        //initialize the upper text
        resume = createTextButton("Resume",1000,100 ,0,30,PauseTable,true);
        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                OverworldScreen.setGameState(OverworldScreen.GameState.RUNNING);
                game.changeScreen(SongOfDeath.ScreenEnum.Overworld);
            }
        });

        loads = createTextButton("Load Game",1000,100 ,0,30,PauseTable,true);
        loads.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                OverworldScreen.setGameState(OverworldScreen.GameState.RUNNING);
                game.changeScreen(SongOfDeath.ScreenEnum.LoadGame);
            }
        });

        options = createTextButton("Options",1000,100 ,0,30,PauseTable,true);
        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                OverworldScreen.setGameState(OverworldScreen.GameState.RUNNING);
                game.changeScreen(SongOfDeath.ScreenEnum.Options);
            }
        });

        mainmenu = createTextButton("Return to Main Menu",1000,100 ,0,30,PauseTable,true);
        mainmenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                OverworldScreen.setGameState(OverworldScreen.GameState.RUNNING);
                game.changeScreen(SongOfDeath.ScreenEnum.MainMenu);
            }
        });
    }

    @Override
    public void show() {
        PauseStage.addActor(PauseTable);
        Gdx.input.setInputProcessor(PauseStage);
        //Gdx.graphics.setCursor(Gdx.graphics.newCursor(game.resourceManager.cursor, 0, 0));
    }

    @Override
    public void render(float delta) {
        //stateTime += Gdx.graphics.getDeltaTime();
        //TextureRegion currentFrame = flowAnimation.getKeyFrame(stateTime, true);

        // the background itself
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(game.getResourceManager().background, 0,0, 1280, 720);
        game.getBatch().end();

        // for the stuff atop the background
        PauseStage.act(delta);
        PauseStage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        PauseTable.remove();
    }
}
