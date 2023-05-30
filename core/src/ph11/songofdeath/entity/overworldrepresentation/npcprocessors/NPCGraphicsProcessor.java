package ph11.songofdeath.entity.overworldrepresentation.npcprocessors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;
import ph11.songofdeath.entity.overworldrepresentation.ProcessorInterface;
import ph11.songofdeath.entity.overworldrepresentation.abstractprocessors.GraphicsProcessor;
import ph11.songofdeath.screens.OverworldScreen;

public class NPCGraphicsProcessor extends GraphicsProcessor {
    private boolean isSelected = false;
    private boolean wasSelected = false;

    private boolean sentShowConversationMessage = false;
    private boolean sentHideConversationMessage = false;

    public NPCGraphicsProcessor() {
        // Nothing
    }

    @Override
    public void receiveMessage(ProcessorInterface.MessageType type, String message) {
        switch (type) {
            case ENTITY_INTERACT:
                isSelected = !wasSelected;
                break;

            case ENTITY_DESELECTED:
                wasSelected = isSelected;
                isSelected = false;
                break;

            case CURRENT_POSITION:
                currentPosition = json.fromJson(Vector2.class, message);
                break;

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
                // nothing for now...

        }
    }

    @Override
    public void render(OverworldScreen screen, OverworldRepresentation entity, float delta) {
        // super.updateAnimations(delta);

        /* For when we have actual proper npcs.
        if (isSelected) {
            drawSelected(entity, mapMgr);
            mapMgr.setCurrentSelectedMapEntity(entity);
            if (!sentShowConversationMessage) {
                notify(json.toJson(entity.getEntityConfig()), ComponentObserver.ComponentEvent.SHOW_CONVERSATION);
                sentShowConversationMessage = true;
                sentHideConversationMessage = false;
            }
        } else {
            if (!sentHideConversationMessage) {
                notify(json.toJson(entity.getEntityConfig()), ComponentObserver.ComponentEvent.HIDE_CONVERSATION);
                sentHideConversationMessage = true;
                sentShowConversationMessage = false;
            }
        }*/

        Batch batch = screen.getBatch();
        batch.begin();
        batch.draw(currentFrame, currentPosition.x, currentPosition.y, 1, 1);
        batch.end();

        //Used to graphically debug boundingboxes
        /*
        Rectangle rect = entity.getCurrentBoundingBox();
        Camera camera = mapMgr.getCamera();
        _shapeRenderer.setProjectionMatrix(camera.combined);
        _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        _shapeRenderer.setColor(Color.BLACK);
        _shapeRenderer.rect(rect.getX() * Map.UNIT_SCALE, rect.getY() * Map.UNIT_SCALE, rect.getWidth() * Map.UNIT_SCALE, rect.getHeight() * Map.UNIT_SCALE);
        _shapeRenderer.end();
        */
    }

    /* NO
    private void drawSelected(Entity entity, MapManager mapMgr) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Camera camera = mapMgr.getCamera();
        Rectangle rect = entity.getCurrentBoundingBox();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.0f, 1.0f, 1.0f, 0.5f);

        float width =  rect.getWidth() * Map.UNIT_SCALE*2f;
        float height = rect.getHeight() * Map.UNIT_SCALE/2f;
        float x = rect.x * Map.UNIT_SCALE - width/4;
        float y = rect.y * Map.UNIT_SCALE - height/2;

        shapeRenderer.ellipse(x,y,width,height);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }*/
}
