package ph11.songofdeath.entity.overworldrepresentation;

import ph11.songofdeath.screens.OverworldScreen;

public interface ProcessorInterface {
    enum MessageType {
        CURRENT_POSITION,
        INIT_START_POSITION,
        RESET_POSITION,
        CURRENT_DIRECTION,
        CURRENT_STATE,
        COLLISION_WITH_MAP,
        COLLISION_WITH_ENTITY,
        COLLISION_WITH_FOE,
        LOAD_ANIMATIONS,
        INIT_DIRECTION,
        INIT_STATE,
        INIT_SELECT_ENTITY,
        ENTITY_SELECTED,
        ENTITY_DESELECTED,
        OPTION_INPUT
    }
    void receiveMessage(MessageType type, String message);
    void render(OverworldScreen screen, OverworldRepresentation entity, float delta);
}
