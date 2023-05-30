package ph11.songofdeath.entity.overworldrepresentation.npcprocessors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;
import ph11.songofdeath.entity.overworldrepresentation.ProcessorInterface;
import ph11.songofdeath.entity.overworldrepresentation.abstractprocessors.InputProcessor;
import ph11.songofdeath.screens.OverworldScreen;

/**
 * Handles movement input for npcs: since they don't move, movement is determined randomly
 */
public class NPCInputProcessor extends InputProcessor {

    // manages whether framing thingy
    private float frameTime = 0.0f;
    private OverworldRepresentation.State currentState;

    public NPCInputProcessor() {
        currentDirection = OverworldRepresentation.Direction.getRandomNext();
        currentState = OverworldRepresentation.State.WALKING;
    }

    @Override
    public void receiveMessage(ProcessorInterface.MessageType type, String message) {
        switch (type) {
            case COLLISION_WITH_MAP:
                currentDirection = OverworldRepresentation.Direction.getRandomNext();
                break;

            case COLLISION_WITH_ENTITY:
                currentState = OverworldRepresentation.State.IDLE;
                break;

            case INIT_STATE:
                currentState = converter.fromJson(OverworldRepresentation.State.class, message);
                break;

            case INIT_DIRECTION:
                currentDirection = converter.fromJson(OverworldRepresentation.Direction.class, message);
                break;
        }
    }

    @Override
    public void render(OverworldScreen screen, OverworldRepresentation entity, float delta) {
        if (keyStatusMap.get(Keys.QUIT)) {
            Gdx.app.exit();
        }

        //If IMMOBILE, don't update anything
        if (currentState == OverworldRepresentation.State.IMMOBILE) {
            entity.sendMessage(MessageType.CURRENT_STATE, converter.toJson(OverworldRepresentation.State.IMMOBILE));
            return;
        }

        frameTime += delta;

        //Change direction after so many seconds
        if (frameTime > MathUtils.random(1,5)) {
            currentState = OverworldRepresentation.State.getRandomNext();
            currentDirection = OverworldRepresentation.Direction.getRandomNext();
            frameTime = 0.0f;
        }

        if (currentState == OverworldRepresentation.State.IDLE) {
            entity.sendMessage(MessageType.CURRENT_STATE, converter.toJson(OverworldRepresentation.State.IDLE));
            return;
        }

        switch(currentDirection) {
            case LEFT:
                entity.sendMessage(MessageType.CURRENT_STATE, converter.toJson(OverworldRepresentation.State.WALKING));
                entity.sendMessage(MessageType.CURRENT_DIRECTION, converter.toJson(OverworldRepresentation.Direction.LEFT));
                break;
            case RIGHT:
                entity.sendMessage(MessageType.CURRENT_STATE, converter.toJson(OverworldRepresentation.State.WALKING));
                entity.sendMessage(MessageType.CURRENT_DIRECTION, converter.toJson(OverworldRepresentation.Direction.RIGHT));
                break;
            case UP:
                entity.sendMessage(MessageType.CURRENT_STATE, converter.toJson(OverworldRepresentation.State.WALKING));
                entity.sendMessage(MessageType.CURRENT_DIRECTION, converter.toJson(OverworldRepresentation.Direction.UP));
                break;
            case DOWN:
                entity.sendMessage(MessageType.CURRENT_STATE, converter.toJson(OverworldRepresentation.State.WALKING));
                entity.sendMessage(MessageType.CURRENT_DIRECTION, converter.toJson(OverworldRepresentation.Direction.DOWN));
                break;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            keyStatusMap.put(Keys.QUIT, true);
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

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
