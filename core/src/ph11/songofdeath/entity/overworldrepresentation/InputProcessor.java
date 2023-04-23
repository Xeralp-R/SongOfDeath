package ph11.songofdeath.entity.overworldrepresentation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ph11.songofdeath.screens.OverworldScreen;

import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.utils.Json;


public class InputProcessor implements ProcessorInterface, com.badlogic.gdx.InputProcessor {
    private boolean interact;
    protected OverworldRepresentation.Direction currentDirection = null;
    Json converter;

    protected enum Keys {
        LEFT, RIGHT, UP, DOWN, QUIT, INTERACT, OPTION
    }

    protected static Map<Keys, Boolean> keyStatusMap = new HashMap<>();
    static {
        keyStatusMap.put(Keys.LEFT, false);
        keyStatusMap.put(Keys.RIGHT, false);
        keyStatusMap.put(Keys.UP, false);
        keyStatusMap.put(Keys.DOWN, false);
        keyStatusMap.put(Keys.QUIT, false);
        keyStatusMap.put(Keys.INTERACT, false);
        keyStatusMap.put(Keys.OPTION, false);
    }

    @Override
    public void receiveMessage(MessageType type, String message) {
        switch (type) {
            case CURRENT_DIRECTION:
                this.currentDirection = converter.fromJson(OverworldRepresentation.Direction.class, message);
                break;

            case OPTION_INPUT:
                //notify("", ComponentObserver.ComponentEvent.OPTION_INPUT);
                break;

            default:
                break;
        }
    }

    @Override
    public void render(OverworldScreen screen, OverworldRepresentation entity, float delta) {
        if (keyStatusMap.get(Keys.LEFT)) {
            entity.sendMessage(MessageType.CURRENT_STATE, converter.toJson(OverworldRepresentation.State.WALKING));
            entity.sendMessage(MessageType.CURRENT_DIRECTION, converter.toJson(OverworldRepresentation.Direction.LEFT));
        }
        else if(keyStatusMap.get(Keys.RIGHT)) {
            entity.sendMessage(MessageType.CURRENT_STATE, converter.toJson(OverworldRepresentation.State.WALKING));
            entity.sendMessage(MessageType.CURRENT_DIRECTION, converter.toJson(OverworldRepresentation.Direction.RIGHT));
        }
        else if(keyStatusMap.get(Keys.UP)) {
            entity.sendMessage(MessageType.CURRENT_STATE, converter.toJson(OverworldRepresentation.State.WALKING));
            entity.sendMessage(MessageType.CURRENT_DIRECTION, converter.toJson(OverworldRepresentation.Direction.UP));
        }
        else if(keyStatusMap.get(Keys.DOWN)) {
            entity.sendMessage(MessageType.CURRENT_STATE, converter.toJson(OverworldRepresentation.State.WALKING));
            entity.sendMessage(MessageType.CURRENT_DIRECTION, converter.toJson(OverworldRepresentation.Direction.DOWN));
        }
        else if(keyStatusMap.get(Keys.QUIT)) {
            //quitReleased();
            Gdx.app.exit();
        }
        else if(keyStatusMap.get(Keys.INTERACT)) {
            //interactReleased();
            interact = true;
        }
        else if(keyStatusMap.get(Keys.OPTION)) {
            entity.sendMessage(MessageType.OPTION_INPUT, "");
            //optionReleased();
        }
        else {
            entity.sendMessage(MessageType.CURRENT_STATE, converter.toJson(OverworldRepresentation.State.IDLE));
            if(currentDirection == null) {
                entity.sendMessage(MessageType.CURRENT_DIRECTION, converter.toJson(OverworldRepresentation.Direction.DOWN));
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.DOWN:
            case Input.Keys.S:
                keyStatusMap.put(Keys.DOWN, true);
                break;

            case Input.Keys.UP:
            case Input.Keys.W:
                keyStatusMap.put(Keys.UP, true);
                break;

            case Input.Keys.LEFT:
            case Input.Keys.A:
                keyStatusMap.put(Keys.LEFT, true);
                break;

            case Input.Keys.RIGHT:
            case Input.Keys.D:
                keyStatusMap.put(Keys.RIGHT, true);
                break;

            case Input.Keys.E:
                keyStatusMap.put(Keys.INTERACT, true);
                break;

            case Input.Keys.O:
                keyStatusMap.put(Keys.OPTION, true);
                break;

            case Input.Keys.ESCAPE:
                keyStatusMap.put(Keys.QUIT, true);
                break;

            default:
                break;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.DOWN:
            case Input.Keys.S:
                keyStatusMap.put(Keys.DOWN, false);
                break;

            case Input.Keys.UP:
            case Input.Keys.W:
                keyStatusMap.put(Keys.UP, false);
                break;

            case Input.Keys.LEFT:
            case Input.Keys.A:
                keyStatusMap.put(Keys.LEFT, false);
                break;

            case Input.Keys.RIGHT:
            case Input.Keys.D:
                keyStatusMap.put(Keys.RIGHT, false);
                break;

            case Input.Keys.E:
                keyStatusMap.put(Keys.INTERACT, false);
                break;

            case Input.Keys.O:
                keyStatusMap.put(Keys.OPTION, false);
                break;

            case Input.Keys.ESCAPE:
                keyStatusMap.put(Keys.QUIT, false);
                break;

            default:
                break;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public static void clear() {
        keyStatusMap.put(Keys.LEFT, false);
        keyStatusMap.put(Keys.RIGHT, false);
        keyStatusMap.put(Keys.UP, false);
        keyStatusMap.put(Keys.DOWN, false);
        keyStatusMap.put(Keys.QUIT, false);
        keyStatusMap.put(Keys.OPTION, false);
    }
}
