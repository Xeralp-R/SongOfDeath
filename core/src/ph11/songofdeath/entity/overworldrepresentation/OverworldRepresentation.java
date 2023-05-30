package ph11.songofdeath.entity.overworldrepresentation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import ph11.songofdeath.entity.overworldrepresentation.abstractprocessors.GraphicsProcessor;
import ph11.songofdeath.entity.overworldrepresentation.abstractprocessors.InputProcessor;
import ph11.songofdeath.entity.overworldrepresentation.abstractprocessors.PhysicsProcessor;
import ph11.songofdeath.screens.OverworldScreen;

public class OverworldRepresentation {
    // useful enums
    public enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT;

        static public Direction getRandomNext() {
            return Direction.values()[MathUtils.random(Direction.values().length - 1)];
        }
        public Direction getOpposite() {
            if (this == LEFT) {
                return RIGHT;
            } else if (this == RIGHT) {
                return LEFT;
            } else if (this == UP) {
                return DOWN;
            } else {
                return UP;
            }
        }
    }

    public enum State {
        IDLE,
        WALKING,
        IMMOBILE;//This should always be last

        static public State getRandomNext() {
            //Ignore IMMOBILE which should be last state
            return State.values()[MathUtils.random(State.values().length - 2)];
        }
    }

    //public final Texture image;
    public final AssetManager assetManager = new AssetManager();

    final private Array<ProcessorInterface> processors;
    final private InputProcessor inputProcessor;
    final private PhysicsProcessor physicsProcessor;
    final private GraphicsProcessor graphicsProcessor;

    // note: these values have not been checked
    public static final int FRAME_WIDTH = 16;
    public static final int FRAME_HEIGHT = 16;

    public OverworldRepresentation(InputProcessor inputProcessor, PhysicsProcessor physicsProcessor, GraphicsProcessor graphicsProcessor) {
        this.inputProcessor = inputProcessor;
        this.graphicsProcessor = graphicsProcessor;
        this.physicsProcessor = physicsProcessor;

        this.processors = new Array<>();
        this.processors.add(inputProcessor, graphicsProcessor, physicsProcessor);
    }

    public void sendMessage(ProcessorInterface.MessageType messageType, String message) {
        for (ProcessorInterface processor : processors) {
            processor.receiveMessage(messageType, message);
        }
    }

    public void registerObserver(ProcessorObserverInterface observer) {
        this.inputProcessor.addObserver(observer);
        this.graphicsProcessor.addObserver(observer);
        this.physicsProcessor.addObserver(observer);
    }

    public void render(OverworldScreen screen, float delta) {
        inputProcessor.render(screen, this, delta);
        physicsProcessor.render(screen, this, delta);
        graphicsProcessor.render(screen, this, delta);
    }

    public Rectangle getBoundingBox() {
        return physicsProcessor.boundingBox;
    }
    public InputProcessor getInputProcessor() { return this.inputProcessor; }
}
