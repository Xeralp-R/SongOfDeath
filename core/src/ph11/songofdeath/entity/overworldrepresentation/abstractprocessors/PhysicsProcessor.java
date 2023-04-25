package ph11.songofdeath.entity.overworldrepresentation.abstractprocessors;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;
import ph11.songofdeath.entity.overworldrepresentation.ProcessorInterface;
import ph11.songofdeath.overworld.AbstractSongOfDeathLevel;


abstract public class PhysicsProcessor implements ProcessorInterface {
    protected Vector2 nextEntityPosition, currentEntityPosition;
    protected OverworldRepresentation.Direction currentDirection;
    protected Json json;
    protected Vector2 velocity;

    public Rectangle boundingBox;
    protected BoundingBoxLocation boundingBoxLocation;
    protected Ray selectionRay;
    protected static final float SELECT_RAY_MAXIMUM_DISTANCE = 32.0f;

    public enum BoundingBoxLocation {
        BOTTOM_LEFT,
        BOTTOM_CENTER,
        CENTER,
    }

    public PhysicsProcessor() {
        this.nextEntityPosition = new Vector2(0,0);
        this.currentEntityPosition = new Vector2(0,0);
        this.velocity = new Vector2(2f,2f);
        this.boundingBox = new Rectangle();
        this.json = new Json();
        boundingBoxLocation = BoundingBoxLocation.BOTTOM_LEFT;
        selectionRay = new Ray(new Vector3(), new Vector3());
    }

    /**
     * Initialized the bounding box as a percentage of the actual height and width of the sprite.
     * This is done to create a more "natural" feeling to the player movement: that is,
     * one can approach a cliff up to their toes, for example, but no greater.
     * @param percentageWidthReduced The percentage of the width of the player actually occupied by the bounding box.
     *                               Valid values only from 0 to 1, exclusive.
     * @param percentageHeightReduced The percentage of the height of the player actually occupied by the bounding box.
     *                                Valid values only from 0 to 1, exclusive.
     */
    protected void initBoundingBox(float percentageWidthReduced, float percentageHeightReduced) {
        //Update the current bounding box
        float width;
        float height;

        float origWidth =  OverworldRepresentation.FRAME_WIDTH;
        float origHeight = OverworldRepresentation.FRAME_HEIGHT;

        float widthReductionAmount = 1.0f - percentageWidthReduced; //.8f for 20% (1 - .20)
        float heightReductionAmount = 1.0f - percentageHeightReduced; //.8f for 20% (1 - .20)

        if(widthReductionAmount > 0 && widthReductionAmount < 1) {
            width = OverworldRepresentation.FRAME_WIDTH * widthReductionAmount;
        } else {
            width = OverworldRepresentation.FRAME_WIDTH;
        }

        if(heightReductionAmount > 0 && heightReductionAmount < 1) {
            height = OverworldRepresentation.FRAME_HEIGHT * heightReductionAmount;
        } else {
            height = OverworldRepresentation.FRAME_HEIGHT;
        }

        if(width == 0 || height == 0) {
            //LOGGER.debug("Width and Height are 0!! " + width + ":" + height);
            // not supposed to be possible!
        }

        // Initializes and actually sets the bounding box
        float minX;
        float minY;

        minX = nextEntityPosition.x / AbstractSongOfDeathLevel.UNIT_SCALE;
        minY = nextEntityPosition.y / AbstractSongOfDeathLevel.UNIT_SCALE;

        boundingBox.setWidth(width);
        boundingBox.setHeight(height);

        switch(boundingBoxLocation) {
            case BOTTOM_LEFT:
                boundingBox.set(minX, minY, width, height);
                break;
            case BOTTOM_CENTER:
                boundingBox.setCenter(minX + origWidth/2, minY + origHeight/4);
                break;
            case CENTER:
                boundingBox.setCenter(minX + origWidth/2, minY + origHeight/2);
                break;
        }
    }

    /**
     * Updates the position of the bounding box to a given position.
     * This can be used to try out a new position, and check if there are any errors
     * or changes associated with that new position.
     * @param position The new position of the player, measured in screen pixels.
     *                 Scaling will automatically be done by this function.
     */
    protected void updateBoundingBoxPosition(Vector2 position) {
        // Convert pixels to map units
        float minX = position.x / AbstractSongOfDeathLevel.UNIT_SCALE;
        float minY = position.y / AbstractSongOfDeathLevel.UNIT_SCALE;

        switch(boundingBoxLocation) {
            case BOTTOM_LEFT:
                boundingBox.set(minX, minY, boundingBox.getWidth(), boundingBox.getHeight());
                break;
            case BOTTOM_CENTER:
                boundingBox.setCenter(minX + OverworldRepresentation.FRAME_WIDTH/2, minY + OverworldRepresentation.FRAME_HEIGHT/4);
                break;
            case CENTER:
                boundingBox.setCenter(minX + OverworldRepresentation.FRAME_WIDTH/2, minY + OverworldRepresentation.FRAME_HEIGHT/2);
                break;
        }
    }

    /**
     * Checks if there is a collision between the collision layer of the map and the entity.
     * @param entity The entity that's being checked.
     * @param level The map on which the entity is and whose collision layer should be checked,
     * @return True if there is a collision, false otherwise.
     */
    protected boolean checkCollisionWithMapLayer(OverworldRepresentation entity, AbstractSongOfDeathLevel level) {
        MapLayer mapCollisionLayer =  level.getCollisionLayer();

        if(mapCollisionLayer == null){
            return false;
        }

        // pre-initialize to make faster
        Rectangle rectangle;

        for(MapObject object: mapCollisionLayer.getObjects()) {
            if(object instanceof RectangleMapObject) {
                rectangle = ((RectangleMapObject)object).getRectangle();
                if(boundingBox.overlaps(rectangle)) {
                    // A collision has been found
                    entity.sendMessage(ProcessorInterface.MessageType.COLLISION_WITH_MAP, "");
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if there is a collision between the given entity and all other entities on the same level.
     * @param entity The entity being checked, as an OverworldRepresentation
     * @param level The level being checked, as an AbstractSongOfDeathLevel
     * @return True if there is a collision, and false otherwise.
     */
    protected boolean checkCollisionWithMapEntities(OverworldRepresentation entity, AbstractSongOfDeathLevel level) {
        Array<OverworldRepresentation> entities = new Array<>();
        entities.addAll(level.getEntities()); // not quite functional
        boolean collision = false;

        for(OverworldRepresentation testEntity : entities) {
            if (testEntity.equals(entity)) {
                continue;
            }

            Rectangle targetRect = testEntity.getBoundingBox();
            if(boundingBox.overlaps(targetRect)) {
                entity.sendMessage(MessageType.COLLISION_WITH_ENTITY, "");
                collision = true;
                break;
            }
        }

        entities.clear();
        return collision;
    }

    /**
     * Sets the next position of the entity, as given in this processor, as the current position.
     * Informs everyone else of the same via a message.
     * @param entity The entity being moved.
     */
    protected void setNextPositionToCurrent(OverworldRepresentation entity) {
        this.currentEntityPosition.x = nextEntityPosition.x;
        this.currentEntityPosition.y = nextEntityPosition.y;

        entity.sendMessage(MessageType.CURRENT_POSITION, json.toJson(currentEntityPosition));
    }

    protected void calculateNextPosition(float deltaTime) {
        if (currentDirection == null) {
            return;
        }

        if (deltaTime > .7) {
            return;
        }

        float testX = currentEntityPosition.x;
        float testY = currentEntityPosition.y;

        velocity.scl(deltaTime);

        switch (currentDirection) {
            case LEFT:
                testX -= velocity.x;
                break;
            case RIGHT:
                testX += velocity.x;
                break;
            case UP:
                testY += velocity.y;
                break;
            case DOWN:
                testY -= velocity.y;
                break;
            default:
                break;
        }

        nextEntityPosition.x = testX;
        nextEntityPosition.y = testY;

        //velocity
        velocity.scl(1 / deltaTime);
    }
}
