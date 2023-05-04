/**
 * The main file, that controls which screen you go to
 * and what you actually see.
 * <p>
 * Lightly (or heavily) adapted from Hugo Descottes
 * GdxGame
 */

package ph11.songofdeath;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ph11.songofdeath.globalmanagers.GlobalResourceManager;
import ph11.songofdeath.overworld.SongOfDeathLevel1;
import ph11.songofdeath.screens.LoadGameScreen;
import ph11.songofdeath.screens.MainMenuScreen;
import ph11.songofdeath.screens.OptionsScreen;
import ph11.songofdeath.screens.PauseScreen;

public class SongOfDeath extends Game implements AbstractSongOfDeath {
	public enum ScreenEnum {
		LoadGame,
		MainMenu,
		Options,
		Overworld,
		Pause,
		Battle
	}
	private SpriteBatch batch;
	public GlobalResourceManager resourceManager;
	private MainMenuScreen mainMenuScreen;
	private OptionsScreen OptionsScreen;
	private LoadGameScreen LoadGameScreen;
	private PauseScreen PauseScreen;
	SongOfDeathLevel1 level1;

	public void changeScreen(ScreenEnum screen_type) {
		// TODO: add transitions
		switch (screen_type) {
			case LoadGame:
				this.setScreen(this.LoadGameScreen);
				break;
			case MainMenu:
				this.setScreen(this.mainMenuScreen);
				break;
			case Options:
				this.setScreen(this.OptionsScreen);
				break;
			case Pause:
				this.setScreen(this.PauseScreen);
				break;
			case Battle:
				//this.setScreen(this.BattleScreen);
				break;
			case Overworld:
				level1 = new SongOfDeathLevel1(this);
				break;
		}
	}

	public void create() {
		this.batch = new SpriteBatch();
		this.resourceManager = GlobalResourceManager.get();

		mainMenuScreen = new MainMenuScreen(this);
		OptionsScreen = new OptionsScreen(this);
		LoadGameScreen = new LoadGameScreen(this);
		PauseScreen = new PauseScreen(this);

		this.setScreen(mainMenuScreen);
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		mainMenuScreen.dispose();
		resourceManager.dispose();
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
