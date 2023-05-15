package ph11.songofdeath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ph11.songofdeath.SongOfDeath;

public class MainMenuScreen extends AbstractScreen {
    private final Table menuTable;
    private final Stage menuStage;
    //private Animation<TextureRegion> flowAnimation;
    //private float stateTime;

    public MainMenuScreen(final SongOfDeath game) {
        super(game);

        // initialize the stage and the table
        menuStage = new Stage(super.viewport, game.getBatch());
        menuTable = createTable();
        //handleBackground();

        // title text
        createLabel("Song of Death", 120, 0, 0, menuTable, true);

        Actor newButton = createTextButton("New Game", 300, 110, 0, 30, menuTable, true);
        newButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                //setScreenWithTransition((BaseScreen) gdxGame.getScreen(), new MenuNewGameScreen(gdxGame, (BaseScreen) gdxGame.getScreen(), resourceManager), new ArrayList<>());
                game.changeScreen(SongOfDeath.ScreenEnum.Overworld);
            }
        });

        Actor loadButton = createTextButton("Battle Screen", 300, 110, 0, 10, menuTable,true);
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                //setScreenWithTransition((BaseScreen) gdxGame.getScreen(), new MenuLoadGameScreen(gdxGame, (BaseScreen) gdxGame.getScreen(), resourceManager), new ArrayList<>());
                game.changeScreen(SongOfDeath.ScreenEnum.Battle);
            }
        });

        Actor optionButton = createTextButton("Options", 300, 110, 0, 10, menuTable,true);
        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                //setScreenWithTransition((BaseScreen) gdxGame.getScreen(), new OptionScreen(gdxGame, (BaseScreen) gdxGame.getScreen(), resourceManager), new ArrayList<>());
                game.changeScreen(SongOfDeath.ScreenEnum.Options);
            }
        });

        Actor exitButton = createTextButton("Exit", 300, 110, 0, 10, menuTable,true);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    /*private void handleBackground() {
        int nbRow = 7;
        int nbCol = 7;
        AnimationManager animationManager = new AnimationManager();

        Texture backgroundSheet = game.resourceManager.background;

        TextureRegion[][] tmp = animationManager.setTextureRegionsDouble(backgroundSheet,
                backgroundSheet.getWidth() / nbCol,
                backgroundSheet.getHeight() / nbRow);

        TextureRegion[] flowFrames = new TextureRegion[nbCol * nbRow];
        int index = 0;
        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                flowFrames[index++] = tmp[i][j];
            }
        }

        flowAnimation = animationManager.setAnimation(flowFrames);
    }*/

    @Override
    public void show() {
        menuStage.addActor(menuTable);
        Gdx.input.setInputProcessor(menuStage);
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
        menuStage.act(delta);
        menuStage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        menuTable.remove();
    }
}
