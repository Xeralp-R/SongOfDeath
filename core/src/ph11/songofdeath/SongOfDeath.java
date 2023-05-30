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
import ph11.songofdeath.battle.internal.battle.BattleManager;
import ph11.songofdeath.battle.internal.entities.Enemy;
import ph11.songofdeath.battle.internal.entities.characters.PartyMembers;
import ph11.songofdeath.battle.internal.location.Location;
import ph11.songofdeath.globalmanagers.GlobalResourceManager;
import ph11.songofdeath.overworld.SongOfDeathLevel1;
import ph11.songofdeath.screens.LoadGameScreen;
import ph11.songofdeath.screens.MainMenuScreen;
import ph11.songofdeath.screens.OptionsScreen;
import ph11.songofdeath.screens.PauseScreen;
import ph11.songofdeath.screens.BattleScreen;

public class SongOfDeath extends Game implements AbstractSongOfDeath {
	public enum ScreenEnum {
		LoadGame,
		MainMenu,
		Options,
		Overworld,
		Pause,
		Battle,
	}
	private SpriteBatch batch;
	public GlobalResourceManager resourceManager;
	private MainMenuScreen mainMenuScreen;
	private OptionsScreen OptionsScreen;
	private LoadGameScreen LoadGameScreen;
	private BattleScreen BattleScreen;
	private PauseScreen PauseScreen;
	SongOfDeathLevel1 level1;

	private Location location;

	public Location getLocation() {
		return this.location;
	}

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
				this.setScreen(this.BattleScreen);
				break;
			case Overworld:
				level1 = new SongOfDeathLevel1(this);
				break;
		}
	}

	public void create() {
		this.batch = new SpriteBatch();
		this.resourceManager = GlobalResourceManager.get();

		initBattleNecessities();
		mainMenuScreen = new MainMenuScreen(this);
		OptionsScreen = new OptionsScreen(this);
		LoadGameScreen = new LoadGameScreen(this);
		BattleScreen = new BattleScreen(this);
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

	private void initBattleNecessities(){
		PartyMembers orpheus = new PartyMembers("Orpheus", 1000, 50, 100, 100, 100,"overworldentities/player/temp-character.png");
		orpheus.addToActiveParty();

		Location desert = new Location("desert");
		this.location = desert;
		PartyMembers.activeParty.setPartyLocation(desert);

		Enemy blueSlime = new Enemy("Blue Slime", "blahblahblah", desert, 25, 200, 10, 50, 50, 100, "overworldentities/enemy/temp-enemy.png");
		Enemy greenSlime = new Enemy("Green Slime", "blahblahblah", desert, 25, 200, 10, 50, 50, 100, "overworldentities/enemy/temp-enemy.png");
		Enemy pinkSlime = new Enemy("Pink Slime", "blahblahblah", desert, 25, 200, 10, 50, 50, 100, "overworldentities/enemy/temp-enemy.png");
		Enemy redSlime = new Enemy("Red Slime", "blahblahblah", desert, 25, 200, 10, 50, 50, 100, "overworldentities/enemy/temp-enemy.png");

		desert.getEnemyList().add(blueSlime);
		desert.getEnemyList().add(greenSlime);
		desert.getEnemyList().add(pinkSlime);
		desert.getEnemyList().add(redSlime);
	}
}
