package ph11.songofdeath.entity.overworldrepresentation;

public interface ProcessorObserverInterface {
    enum ProcessorEvent {
        LOAD_CONVERSATION,
        SHOW_CONVERSATION,
        HIDE_CONVERSATION,
        LOAD_RESUME,
        SHOW_RESUME,
        QUEST_LOCATION_DISCOVERED,
        ENEMY_SPAWN_LOCATION_CHANGED,
        START_BATTLE,
        OPTION_INPUT
    }

    void onNotify(final String value, ProcessorEvent event);
}
