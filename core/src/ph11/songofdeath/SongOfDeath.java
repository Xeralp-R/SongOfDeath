/**
 * The main file, that controls which screen you go to
 * and what you actually see.
 *
 * Lightly (or heavily) adapted from Hugo Descottes
 * GdxGame
 */

package ph11.songofdeath;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ph11.songofdeath.globalmanagers.GlobalResourceManager;
import ph11.songofdeath.screens.MainMenuScreen;

public class SongOfDeath extends Game {
	public enum ScreenEnum {
		MainMenu,
		Options,
		Overworld,
		Battle
	}
	private SpriteBatch batch;
	public GlobalResourceManager resourceManager;
	private MainMenuScreen mainMenuScreen;

	public SpriteBatch getBatch() {
		return batch;
	}

	public void changeScreen(ScreenEnum screen_type) {
		// TODO: add transitions
		switch (screen_type) {
			case MainMenu:
				this.setScreen(this.mainMenuScreen);
				break;
			case Options:
				//this.setScreen(this.OptionsScreen);
				break;
			case Battle:
				//this.setScreen(this.BattleScreen);
				break;
			case Overworld:
				//this.setScreen(this.OverworldScreen);
				break;
		}
	}

	public void create() {
		batch = new SpriteBatch();
		this.resourceManager = GlobalResourceManager.get();

		mainMenuScreen = new MainMenuScreen(this);

		this.setScreen(mainMenuScreen);
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		mainMenuScreen.dispose();
		resourceManager.dispose();
	}
}
