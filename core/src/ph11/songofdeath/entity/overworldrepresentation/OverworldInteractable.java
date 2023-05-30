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
    private final String dialogText; // TODO: make more efficient
    private final InteractionResult interactionResult;

    public OverworldInteractable(Rectangle objectRectangle, String dialogText, InteractionResult interactionResult) {
        this.interactionRectangle = objectRectangle;
        this.dialogText = dialogText;
        this.interactionResult = interactionResult;
    }

    public Rectangle getInteractionRectangle() {
        return interactionRectangle;
    }

    public Array<String> getDialogText() {

        return new Array<>(dialogText.split("|"));
    }

    public InteractionResult getInteractionResult() {
        return interactionResult;
    }
}
