package ph11.songofdeath.entity.overworldrepresentation.abstractprocessors;

import com.badlogic.gdx.utils.Json;
import ph11.songofdeath.entity.overworldrepresentation.AbstractProcessor;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;
import ph11.songofdeath.entity.overworldrepresentation.ProcessorInterface;

import java.util.HashMap;
import java.util.Map;

abstract public class InputProcessor extends AbstractProcessor implements ProcessorInterface, com.badlogic.gdx.InputProcessor {

    protected static Map<Keys, Boolean> keyStatusMap = new HashMap<>();
    protected OverworldRepresentation.Direction currentDirection = null;
    protected Json converter = new Json();

    protected enum Keys {
        LEFT, RIGHT, UP, DOWN, QUIT, INTERACT, OPTION
    }

    static {
        keyStatusMap.put(Keys.LEFT, false);
        keyStatusMap.put(Keys.RIGHT, false);
        keyStatusMap.put(Keys.UP, false);
        keyStatusMap.put(Keys.DOWN, false);
        keyStatusMap.put(Keys.QUIT, false);
        keyStatusMap.put(Keys.INTERACT, false);
        keyStatusMap.put(Keys.OPTION, false);
    }
}
