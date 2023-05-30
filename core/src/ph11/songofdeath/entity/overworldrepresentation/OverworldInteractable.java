package ph11.songofdeath.entity.overworldrepresentation;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class OverworldInteractable {
    public enum InteractionResult {
        NoResult,
        Battle;

        public static int encode(InteractionResult i) {
            switch (i) {
                case NoResult:
                    return 0;
                case Battle:
                    return 1;
            }
            return -1;
        }

        public static InteractionResult recode(int i) {
            if (i == 0) {
                return NoResult;
            }
            if (i == 1) {
                return Battle;
            }
            throw new RuntimeException("unknown interaction result value");
        }
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

    static private String delimiter = "|[]|";

    public static String convertToString(OverworldInteractable object) {
        String test = "";
        test += object.interactionRectangle.x + delimiter;
        test += object.interactionRectangle.y + delimiter;
        test += object.interactionRectangle.width + delimiter;
        test += object.interactionRectangle.height + delimiter;
        test += object.dialogText + delimiter;
        test += Integer.toString(InteractionResult.encode(object.interactionResult));

        return test;
    }

    public static OverworldInteractable convertFromString(String input) {
        Array<String> array = new Array<>(input.split(delimiter));
        return new OverworldInteractable(new Rectangle(Inte))
    }
}
