package ph11.songofdeath.entity.overworldrepresentation.npcprocessors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;
import ph11.songofdeath.entity.overworldrepresentation.ProcessorInterface;
import ph11.songofdeath.entity.overworldrepresentation.abstractprocessors.PhysicsProcessor;
import ph11.songofdeath.overworld.AbstractSongOfDeathLevel;
import ph11.songofdeath.screens.OverworldScreen;

public class NPCPhysicsProcessor extends PhysicsProcessor {

    private OverworldRepresentation.State state;

    public NPCPhysicsProcessor() {
        boundingBoxLocation = BoundingBoxLocation.CENTER;
        initBoundingBox(0f, 0f);
    }

    @Override
    public void receiveMessage(ProcessorInterface.MessageType type, String message) {
        switch (type) {
            case INIT_START_POSITION:
                currentEntityPosition = json.fromJson(Vector2.class, message);
                nextEntityPosition.set(currentEntityPosition.x, currentEntityPosition.y);
                break;

            case CURRENT_STATE:
                state = json.fromJson(OverworldRepresentation.State.class, message);
                break;

            case CURRENT_DIRECTION:
                currentDirection = json.fromJson(OverworldRepresentation.Direction.class, message);
                break;

        }
    }

    @Override
    public void render(OverworldScreen screen, OverworldRepresentation entity, float delta) {
        updateBoundingBoxPosition(nextEntityPosition);

        if (state == OverworldRepresentation.State.IMMOBILE) {
            return;
        }

        if (!checkCollisionWithMapLayer(entity, screen.getLevel()) &&
                !this.checkCollisionWithMapEntities(entity, screen.getLevel()) &&
                state == OverworldRepresentation.State.WALKING) {
            setNextPositionToCurrent(entity);
        } else {
            updateBoundingBoxPosition(currentEntityPosition);
        }

        calculateNextPosition(delta);
    }

    @Override
    protected boolean checkCollisionWithMapEntities(OverworldRepresentation entity, AbstractSongOfDeathLevel level) {
        // Test against player, because the player isn't automatically included in the other entities
        if (isCollision(entity, level.getPlayerRepresentation())) {
            return true;
        }

        return super.checkCollisionWithMapEntities(entity, level);
    }
}
