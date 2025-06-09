package fr.esiea.mali.core.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventBus {
    private final Map<Class<? extends IEvent>, List<Consumer<? extends IEvent>>> listeners = new HashMap<>();

    public <E extends IEvent> void register(Class<E> type, Consumer<E> listener) {
        this.listeners.computeIfAbsent(type, _ -> new ArrayList<>()).add(listener);
    }

    @SuppressWarnings("unchecked")
    public <E extends IEvent> void post(E event) {
        List<Consumer<? extends IEvent>> list = listeners.get(event.getClass());
        if (list != null) {
            for (Consumer<? extends IEvent> listener : list) {
                ((Consumer<E>) listener).accept(event);
            }
        }
    }
}
