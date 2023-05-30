package ph11.songofdeath.entity.overworldrepresentation;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class OverworldInteractable {
    public enum InteractionResult {
        NoResult,
        Battle
    }

    // note: these values have not been checked
    public static final int FRAME_WIDTH = 16;
    public static final int FRAME_HEIGHT = 16;

    private final Rectangle interactionRectangle;
    private final Array<String> dialogText; // TODO: make more efficient
    private final InteractionResult interactionResult;

    public OverworldInteractable(Rectangle objectRectangle, Array<String> dialogText, InteractionResult interactionResult) {
        this.interactionRectangle = objectRectangle;
        this.dialogText = dialogText;
        this.interactionResult = interactionResult;
    }

    public Rectangle getInteractionRectangle() {
        return interactionRectangle;
    }

    public Array<String> getDialogText() {
        return dialogText;
    }

    public InteractionResult getInteractionResult() {
        return interactionResult;
    }
}
