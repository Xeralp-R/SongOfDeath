package ph11.songofdeath.entity.overworldrepresentation.playerprocessors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;
import ph11.songofdeath.entity.overworldrepresentation.abstractprocessors.GraphicsProcessor;
import ph11.songofdeath.screens.OverworldScreen;

public class PlayerGraphicsProcessor extends GraphicsProcessor {
    protected Vector2 previousPosition;

    public PlayerGraphicsProcessor() {
        this.previousPosition = new Vector2(0,0);
        super.loadFrame("overworldentitities/player/temp-character.png");
    }

    @Override
    public void render(OverworldScreen screen, OverworldRepresentation entity, float delta) {
        //super.updateAnimations(delta);

        // If player has moved, correct the positions
        if (this.previousPosition.x != super.currentPosition.x ||
                this.previousPosition.y != super.currentPosition.y) {
            this.previousPosition = super.currentPosition.cpy();
        }

        Camera camera = screen.getDefaultCamera();
        camera.position.set(currentPosition.x, currentPosition.y, 0f);
        camera.update();

        Batch batch = screen.getBatch();
        batch.begin();
        batch.draw(super.currentFrame, currentPosition.x, currentPosition.y, 1, 1);
        batch.end();
    }

    @Override
    public void receiveMessage(MessageType type, String message) {
        switch (type) {
            case RESET_POSITION:
                this.currentPosition = null;
                break;
            case CURRENT_POSITION:
            case INIT_START_POSITION:
                currentPosition = json.fromJson(Vector2.class, message);
                break;
            case CURRENT_STATE:
                currentState = json.fromJson(OverworldRepresentation.State.class, message);
                break;
            case CURRENT_DIRECTION:
                currentDirection = json.fromJson(OverworldRepresentation.Direction.class, message);
                break;
            case LOAD_ANIMATIONS:
                // do something?
            default:
                break;
        }
    }
}
