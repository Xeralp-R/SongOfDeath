package ph11.songofdeath.entity.overworldrepresentation.npcprocessors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.gdx.game.entities.Entity;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;
import ph11.songofdeath.entity.overworldrepresentation.ProcessorInterface;
import ph11.songofdeath.entity.overworldrepresentation.abstractprocessors.InputProcessor;

/**
 * Handles movement input for npcs: since they don't move, movement is determined randomly
 */
public class NPCInputProcessor extends InputProcessor {

    private float frameTime = 0.0f;
    private OverworldRepresentation.State currentState;

    public NPCInputProcessor() {
        currentDirection = OverworldRepresentation.Direction.getRandomNext();
        currentState = OverworldRepresentation.State.WALKING;
    }

    @Override
    public void receiveMessage(ProcessorInterface.MessageType type, String message) {
        switch ()

        //Specifically for messages with 1 object payload
        if (string.length == 1) {
            if (string[0].equalsIgnoreCase(MESSAGE.COLLISION_WITH_MAP.toString())) {
                currentDirection = Entity.Direction.getRandomNext();
            } else if (string[0].equalsIgnoreCase(MESSAGE.COLLISION_WITH_ENTITY.toString())) {
                currentState = Entity.State.IDLE;
            }
        }

        if (string.length == 2) {
            if (string[0].equalsIgnoreCase(MESSAGE.INIT_STATE.toString())) {
                currentState = json.fromJson(Entity.State.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_DIRECTION.toString())) {
                currentDirection = json.fromJson(Entity.Direction.class, string[1]);
            }
        }
    }

    @Override
    public void dispose() {
        // Nothing
    }

    @Override
    public void update(Entity entity, float delta) {
        if (keys.get(Keys.QUIT)) {
            Gdx.app.exit();
        }

        //If IMMOBILE, don't update anything
        if (currentState == Entity.State.IMMOBILE) {
            entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.IMMOBILE));
            return;
        }

        frameTime += delta;

        //Change direction after so many seconds
        if (frameTime > MathUtils.random(1,5)) {
            currentState = Entity.State.getRandomNext();
            currentDirection = Entity.Direction.getRandomNext();
            frameTime = 0.0f;
        }

        if (currentState == Entity.State.IDLE) {
            entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.IDLE));
            return;
        }

        switch(currentDirection) {
            case LEFT:
                entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.WALKING));
                entity.sendMessage(MESSAGE.CURRENT_DIRECTION, json.toJson(Entity.Direction.LEFT));
                break;
            case RIGHT:
                entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.WALKING));
                entity.sendMessage(MESSAGE.CURRENT_DIRECTION, json.toJson(Entity.Direction.RIGHT));
                break;
            case UP:
                entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.WALKING));
                entity.sendMessage(MESSAGE.CURRENT_DIRECTION, json.toJson(Entity.Direction.UP));
                break;
            case DOWN:
                entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.WALKING));
                entity.sendMessage(MESSAGE.CURRENT_DIRECTION, json.toJson(Entity.Direction.DOWN));
                break;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            keys.put(Keys.QUIT, true);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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
}
