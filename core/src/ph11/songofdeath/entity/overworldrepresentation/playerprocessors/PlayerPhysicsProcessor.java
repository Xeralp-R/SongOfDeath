package ph11.songofdeath.entity.overworldrepresentation.playerprocessors;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;
import ph11.songofdeath.entity.overworldrepresentation.abstractprocessors.PhysicsProcessor;
import ph11.songofdeath.overworld.AbstractSongOfDeathLevel;
import ph11.songofdeath.screens.OverworldScreen;

public class PlayerPhysicsProcessor extends PhysicsProcessor {
    private OverworldRepresentation.State state;
    private String previousDiscovery;
    private String previousEnemySpawn;

    public PlayerPhysicsProcessor() {
        super.velocity = new Vector2(5f, 5f);
        boundingBoxLocation = PhysicsProcessor.BoundingBoxLocation.BOTTOM_CENTER;
        initBoundingBox(0.3f, 0.5f);
        previousDiscovery = "";
        previousEnemySpawn = "0";
    }

    @Override
    public void render(OverworldScreen screen, OverworldRepresentation entity, float delta) {
        AbstractSongOfDeathLevel level = screen.getLevel();

        // try out the next position
        super.updateBoundingBoxPosition(nextEntityPosition);

        this.checkPortalLayerActivation(level);

        boolean hasCollidedMapBoundaries = super.checkCollisionWithMapLayer(entity, level);
        boolean hasCollidedMapEntities = false; //super.checkCollisionWithMapEntities(entity, level);
        boolean isWalking = (state == OverworldRepresentation.State.WALKING);
        if (!hasCollidedMapBoundaries && !hasCollidedMapEntities && isWalking) {
            super.setNextPositionToCurrent(entity);

            screen.getCamera().position.set(currentEntityPosition, 0f);
            screen.getCamera().update();
        }
        else {
            super.updateBoundingBoxPosition(currentEntityPosition);
        }

        super.calculateNextPosition(delta);
    }

    private boolean checkPortalLayerActivation(AbstractSongOfDeathLevel level) {
        MapLayer mapPortalLayer =  level.getPortalLayer();

        if(mapPortalLayer == null) {
            return false;
        }

        // minimize initializes
        Rectangle rectangle;

        for(MapObject object: mapPortalLayer.getObjects()) {
            if(object instanceof RectangleMapObject) {
                rectangle = ((RectangleMapObject)object).getRectangle();

                if(boundingBox.overlaps(rectangle)) {
                    String mapName = object.getName();
                    if(mapName == null) {
                        return false;
                    }

                    // somehow go to the next map: to be implemented
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void receiveMessage(MessageType type, String message) {
        switch (type) {
            case INIT_START_POSITION:
                super.currentEntityPosition = json.fromJson(Vector2.class, message);
                super.nextEntityPosition.set(currentEntityPosition.x, currentEntityPosition.y);
                this.previousDiscovery = "";
                this.previousEnemySpawn = "0";
                break;

            case CURRENT_STATE:
                this.state = json.fromJson(OverworldRepresentation.State.class, message);
                break;

            case CURRENT_DIRECTION:
                super.currentDirection = json.fromJson(OverworldRepresentation.Direction.class, message);
                break;

            default:
                // all other situations have been compressed
                break;
        }
    }
}
