package ph11.songofdeath.entity.overworldrepresentation.abstractprocessors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import ph11.songofdeath.entity.overworldrepresentation.OverworldRepresentation;
import ph11.songofdeath.entity.overworldrepresentation.ProcessorInterface;
import ph11.songofdeath.globalmanagers.GlobalResourceManager;

abstract public class GraphicsProcessor implements ProcessorInterface {
    protected TextureRegion currentFrame = null;
    protected float frameTime = 0f;
    protected OverworldRepresentation.State currentState;
    protected OverworldRepresentation.Direction currentDirection;
    protected Json json;
    protected Vector2 currentPosition;
    protected ShapeRenderer shapeRenderer;

    protected GraphicsProcessor() {
        currentPosition = new Vector2(0,0);
        currentState = OverworldRepresentation.State.WALKING;
        currentDirection = OverworldRepresentation.Direction.DOWN;
        json = new Json();
        //animations = new Hashtable<>();
        shapeRenderer = new ShapeRenderer();
    }

    protected void loadFrame(String textureName) {
        GlobalResourceManager.loadTextureAsset(textureName);
        this.currentFrame = new TextureRegion(GlobalResourceManager.getTextureAsset(textureName));
    }

}
