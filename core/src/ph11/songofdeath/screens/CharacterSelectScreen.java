package ph11.songofdeath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ph11.songofdeath.SongOfDeath;

import javax.xml.soap.Text;

public class CharacterSelectScreen extends AbstractScreen {
    private final Table charTable;
    private final Stage charStage;
    private Actor btn1, btn2, back, start;
    public CharacterSelectScreen(final SongOfDeath game) {
        super(game);

        charStage = new Stage(super.viewport, game.getBatch());
        charTable = createTable();

        createLabel("",0,0,0,charTable,false); //temporary filler
        createLabel("",0,0,0,charTable,false); //temporary filler
        createLabel("Select Character", 120, 0, 0, charTable, false);
        createLabel("",0,0,0,charTable,false); //temporary filler
        createLabel("",0,0,0,charTable,true); //temporary filler

        createLabel("",0,0,0,charTable,false); //temporary filler
        btn1 = createTextButton("<",100,100,0,30,charTable,false);
        createLabel("*insert character*", 40, 0,30, charTable,false);
        btn2 = createTextButton(">",100,100,10,30,charTable,false);
        createLabel("",0,0,0,charTable,true); //temporary filler

        createLabel("",0,0,0,charTable,false); //temporary filler
        createLabel("Name:",50,0,50,charTable,false);
        createLabel("*textfield*", 100,0,50,charTable,false);
        createLabel("",0,0,0,charTable,false); //temporary filler
        createLabel("",0,0,0,charTable,true); //temporary filler

        back = createTextButton("Back", 0,50, charTable,false);
        createLabel("",0,0,0,charTable,false); //temporary filler
        createLabel("",0,0,0,charTable,false); //temporary filler
        createLabel("",0,0,0,charTable,false); //temporary filler
        start = createTextButton("Next", 0,50, charTable,false);

        back.addListener(new ClickListener() {
            public void clicked(InputEvent even, float x, float y) {
                //setScreenWithTransition((BaseScreen) gdxGame.getScreen(), new MenuNewGameScreen(gdxGame, (BaseScreen) gdxGame.getScreen(), resourceManager), new ArrayList<>());
                game.changeScreen(SongOfDeath.ScreenEnum.MainMenu);
            }
        });
        start.addListener(new ClickListener() {
            public void clicked(InputEvent even, float x, float y) {
                //setScreenWithTransition((BaseScreen) gdxGame.getScreen(), new MenuNewGameScreen(gdxGame, (BaseScreen) gdxGame.getScreen(), resourceManager), new ArrayList<>());
                game.changeScreen(SongOfDeath.ScreenEnum.Overworld);
            }
        });
        //charTable.setDebug(true);
    }
    @Override
    public void show() {
        charStage.addActor(charTable);
        Gdx.input.setInputProcessor(charStage);
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
        charStage.act(delta);
        charStage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        charTable.remove();
    }
}
