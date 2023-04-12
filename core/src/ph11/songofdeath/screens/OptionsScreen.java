package ph11.songofdeath.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ph11.songofdeath.SongOfDeath;

public class OptionsScreen extends AbstractScreen{
    private final Stage OptionStage;
    private final Table OptionTable;
    private String bgm = "1", sfx = "1"; //initialize the bgm and sfx volume
    private Actor lowerBGM, higherBGM, lowerSFX, higherSFX;
    public OptionsScreen(final SongOfDeath game){
        super(game);

        OptionStage = new Stage(super.viewport, game.getBatch());
        OptionTable = createTable();

        //initialize the upper text
        createLabel("Options", 120, 385, -100, OptionTable, true);
        createLabel("---------------", 50, 385,-20,OptionTable, true);

        createLabel("BGM", 100,-300,100,OptionTable, false);
        final Label BGM = createLabel(bgm, 100,100,100,OptionTable,false);
        lowerBGM = createTextButton("<", 100, 100, 50, 100, OptionTable, false);
        lowerBGM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                changeBGM(false);
                BGM.setText(bgm);
            }
        });
        higherBGM = createTextButton(">", 100, 100, 5, 100, OptionTable,true);
        higherBGM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                changeBGM(true);
                BGM.setText(bgm);
            }
        });


        createLabel("Sound FX", 100, -300,0,OptionTable,false);
        final Label SFX = createLabel(sfx, 100,100,5,OptionTable,false);
        lowerSFX = createTextButton("<", 100, 100, 50, 30, OptionTable,false);
        lowerSFX.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                changeSFX(false);
                SFX.setText(sfx);
            }
        });
        higherSFX = createTextButton(">", 100, 100, 5, 30, OptionTable,false);
        higherSFX.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent even, float x, float y) {
                changeSFX(true);
                SFX.setText(sfx);
            }
        });
    }

    public void changeBGM(boolean increase) { //changes the bgm volume by 1
        int bgmVol = Integer.parseInt(bgm);
        if (increase == true) {
            bgmVol += 1;
        } else {
            bgmVol -= 1;
        }
        bgm = String.valueOf(bgmVol);
        checkLimit();
    }

    public void changeSFX(boolean increase) { //changes the sfx volume by 1
        int sfxVol = Integer.parseInt(sfx);
        if (increase == true) {
            sfxVol += 1;
        } else {
            sfxVol -= 1;
        }
        sfx = String.valueOf(sfxVol);
        checkLimit();
    }

    public void checkLimit(){ //checks if bgm and sfx volume are within [0,100]
        if(bgm.equals("0")) lowerBGM.setTouchable(Touchable.disabled);
        else lowerBGM.setTouchable(Touchable.enabled);
        if(bgm.equals("100")) higherBGM.setTouchable(Touchable.disabled);
        else higherBGM.setTouchable(Touchable.enabled);
        if(sfx.equals("0")) lowerSFX.setTouchable(Touchable.disabled);
        else lowerSFX.setTouchable(Touchable.enabled);
        if(sfx.equals("100")) higherSFX.setTouchable(Touchable.disabled);
        else higherSFX.setTouchable(Touchable.enabled);
    }

    @Override
    public void show() {
        OptionStage.addActor(OptionTable);
        Gdx.input.setInputProcessor(OptionStage);
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
        OptionStage.act(delta);
        OptionStage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        OptionTable.remove();
    }
}
