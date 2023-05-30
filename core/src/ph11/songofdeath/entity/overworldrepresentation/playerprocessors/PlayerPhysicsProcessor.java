package ph11.songofdeath.entity.overworldrepresentation.playerprocessors;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import ph11.songofdeath.entity.overworldrepresentation.OverworldInteractable;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;
import ph11.songofdeath.entity.overworldrepresentation.ProcessorObserverInterface;
import ph11.songofdeath.entity.overworldrepresentation.abstractprocessors.PhysicsProcessor;
import ph11.songofdeath.overworld.AbstractSongOfDeathLevel;
import ph11.songofdeath.screens.OverworldScreen;

public class PlayerPhysicsProcessor extends PhysicsProcessor {
    private OverworldRepresentation.State state;
    private String previousDiscovery;
    private String previousEnemySpawn;
    private Json classCompressor;

    // stuff to check interaction
    private static final float SELECT_RAY_MAXIMUM_DISTANCE = 32.0f;
    private boolean checkInteract = true;
    private Vector2 intersectionRayRoot;
    private Vector2 intersectionRayTip;


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

        // check if we'll change screen
        this.checkPortalLayerActivation(level);
        // check if we'll interact
        if (this.checkInteract) {
            this.checkInteractionInteractableCandidate(level);
            this.checkInteract = false;
        }

        // check if there is a collision
        boolean hasCollidedMapBoundaries = super.checkCollisionWithMapLayer(entity, level);
        boolean hasCollidedMapEntities = super.checkCollisionWithMapEntities(entity, level);
        boolean isWalking = (state == OverworldRepresentation.State.WALKING);
        // if there is none, move the player to that position
        if (!hasCollidedMapBoundaries && !hasCollidedMapEntities && isWalking) {
            super.setNextPositionToCurrent(entity);

            screen.getCamera().position.set(currentEntityPosition, 0f);
            screen.getCamera().update();
        }
        // otherwise, move the player back to the old position
        else {
            super.updateBoundingBoxPosition(currentEntityPosition);
        }

        super.calculateNextPosition(delta);
    }

    private void checkPortalLayerActivation(AbstractSongOfDeathLevel level) {
        MapLayer mapPortalLayer =  level.getPortalLayer();

        if(mapPortalLayer == null) {
            return;
        }

        // minimize initializes
        Rectangle rectangle;

        for(MapObject object: mapPortalLayer.getObjects()) {
            if(object instanceof RectangleMapObject) {
                rectangle = ((RectangleMapObject)object).getRectangle();

                if(boundingBox.overlaps(rectangle)) {
                    String mapName = object.getName();
                    if(mapName == null) {
                        return;
                    }

                    // TODO: somehow go to the next map
                    return;
                }
            }
        }
    }

    private void checkInteractionInteractableCandidate(AbstractSongOfDeathLevel level) {
        Array<OverworldInteractable> interactables = new Array<>();
        interactables.addAll(level.getInteractables());

        this.intersectionRayRoot = super.nextEntityPosition;
        switch (super.currentDirection) {
            case UP:
                this.intersectionRayTip = intersectionRayRoot.add(0, SELECT_RAY_MAXIMUM_DISTANCE);
                break;
            case DOWN:
                this.intersectionRayTip = intersectionRayRoot.add(0, -SELECT_RAY_MAXIMUM_DISTANCE);
                break;
            case RIGHT:
                this.intersectionRayTip = intersectionRayRoot.add(SELECT_RAY_MAXIMUM_DISTANCE, 0);
                break;
            case LEFT:
                this.intersectionRayTip = intersectionRayRoot.add(-SELECT_RAY_MAXIMUM_DISTANCE, 0);
                break;
        }

        // there might be more than one rectangle that's close, in which case only one may be selected
        Array<OverworldInteractable> interactionCandidates = new Array<>();

        for (OverworldInteractable interactable : interactables) {
            if (Intersector.intersectSegmentRectangle(intersectionRayRoot,intersectionRayTip,interactable.getInteractionRectangle())) {
                interactionCandidates.add(interactable);
            }
        }

        if (interactionCandidates.isEmpty()) {
            return;
        }

        // determine which of the interaction candidates is closest
        OverworldInteractable closestInteractable = interactionCandidates.get(0);
        Vector2 interactableCenter = new Vector2();
        float currentDistance = 1000.00f;
        for (OverworldInteractable interactable : interactionCandidates) {
            interactable.getInteractionRectangle().getCenter(interactableCenter);

            float tentativeDistance = interactableCenter.dst(intersectionRayRoot);
            if (tentativeDistance < currentDistance) {
                currentDistance = tentativeDistance;
                closestInteractable = interactable;
            }
        }

        // notify the exterior
        super.notify(this.json.toJson(closestInteractable), ProcessorObserverInterface.ProcessorEvent.LOAD_CONVERSATION);
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

            case INIT_INTERACT_ENTITY:
                super.currentDirection = json.fromJson(OverworldRepresentation.Direction.class, message);
                this.checkInteract = true;
                break;

            default:
                // all other situations have been compressed
                break;
        }
    }
}
