package ph11.songofdeath.entity.overworldrepresentation;


import com.badlogic.gdx.math.Rectangle;

public class OverworldInteractable {
    // note: these values have not been checked
    public static final int FRAME_WIDTH = 16;
    public static final int FRAME_HEIGHT = 16;

    private final Rectangle interactionRectangle;
    private final String dialogText; // TODO: make more efficient

    public OverworldInteractable(Rectangle objectRectangle, String dialogText) {
        this.interactionRectangle = objectRectangle;
        this.dialogText = dialogText;
    }

    public Rectangle getInteractionRectangle() {
        return interactionRectangle;
    }
}
