package ph11.songofdeath.entity.overworldrepresentation;

import com.badlogic.gdx.utils.Array;

public class AbstractProcessor {
    private Array<ProcessorObserverInterface> observers;

    public AbstractProcessor() {
        observers = new Array<>();
    }

    public void addObserver(ProcessorObserverInterface conversationObserver) {
        observers.add(conversationObserver);
    }

    public void removeObserver(ProcessorObserverInterface conversationObserver) {
        observers.removeValue(conversationObserver, true);
    }

    public void removeAllObservers() {
        for(ProcessorObserverInterface observer: observers) {
            observers.removeValue(observer, true);
        }
    }

    protected void notify(final String value, ProcessorObserverInterface.ProcessorEvent event) {
        for(ProcessorObserverInterface observer: observers) {
            observer.onNotify(value, event);
        }
    }
}
