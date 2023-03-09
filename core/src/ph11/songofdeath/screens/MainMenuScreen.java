package ph11.songofdeath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ph11.songofdeath.SongOfDeath;

import java.util.ArrayList;

public class MainMenuScreen extends AbstractScreen {
    private Table menuTable;
    private Stage menuStage = new Stage();
    private Animation<TextureRegion> flowAnimation;
    private float stateTime;

    public MainMenuScreen(final SongOfDeath game) {
        super(game);

        menuTable = createTable();
        //handleBackground();
        super.createButton("New Game", 0, menuTable.getHeight()/10, menuTable);

        Actor newButton = menuTable.getCells().get(0).getActor();
        newButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                //setScreenWithTransition((BaseScreen) gdxGame.getScreen(), new MenuNewGameScreen(gdxGame, (BaseScreen) gdxGame.getScreen(), resourceManager), new ArrayList<>());
                game.changeScreen(SongOfDeath.ScreenEnum.Overworld);
            }
        });

        createButton("Load Game", 0, menuTable.getHeight()/15, menuTable);

        Actor loadButton = menuTable.getCells().get(1).getActor();
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                //setScreenWithTransition((BaseScreen) gdxGame.getScreen(), new MenuLoadGameScreen(gdxGame, (BaseScreen) gdxGame.getScreen(), resourceManager), new ArrayList<>());
                game.changeScreen(SongOfDeath.ScreenEnum.Overworld);
            }
        });

        createButton("Options", 0, menuTable.getHeight()/10, menuTable);

        Actor optionButton = menuTable.getCells().get(2).getActor();
        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                //setScreenWithTransition((BaseScreen) gdxGame.getScreen(), new OptionScreen(gdxGame, (BaseScreen) gdxGame.getScreen(), resourceManager), new ArrayList<>());
                game.changeScreen(SongOfDeath.ScreenEnum.Options);
            }
        });

        createButton("Exit", 0, menuTable.getHeight()/9, menuTable);

        Actor exitButton = menuTable.getCells().get(3).getActor();
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

    private void handleExitButton() {

    }

    private void handleOptionButton() {

    }

    private void handleNewButton() {

    }

    private void handleLoadButton() {

    }

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

        game.getBatch().begin();
        game.getBatch().draw(game.resourceManager.background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBatch().end();

        /*if (!game.resourceManager.isOptionScreen() && !resourceManager.isMenuNewGameScreen() && !resourceManager.isMenuLoadGameScreen()) {
            menuStage.act(delta);
            menuStage.draw();
        }*/
    }

    @Override
    public void dispose() {
        super.dispose();
        menuTable.remove();
    }
}
