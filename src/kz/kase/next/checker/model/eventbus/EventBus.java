package kz.kase.next.checker.model.eventbus;


import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
    private final static EventBus instance = new EventBus();

    private final List<Listener> listeners =
            new CopyOnWriteArrayList<>();

    private static final Logger log = Logger.getLogger(EventBus.class);

    public EventBus() {
        System.out.println("Event bus initialing");
    }

    public static EventBus getInstance() {
        return instance;
    }

    public void addListener(Listener l) {
        log.debug("Adding a listener " + l.getClass().getSimpleName());
        listeners.add(l);
    }

    public void removeListener(Listener l) {
        if (listeners.contains(l)) {
            log.debug("Removing a listener " + l.getClass().getSimpleName());
            listeners.remove(l);
        }
    }

    public void fireOnEvent(final String eventName) {
        fireOnEvent(eventName, null);
    }

    public void fireOnEvent(final String eventName, final Object data) {
        log.debug("Get event :\n" + eventName + "\n" + data);
        for (Listener l : listeners) {
            try {
                l.onEvent(eventName, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static interface Listener {
        void onEvent(String eventName, Object data);
    }

    public List<Listener> getListeners() {
        return listeners;
    }


    //    private static final EventBus instance = new EventBus();
//
//    public static EventBus getInstance() {
//        return instance;
//    }
}
