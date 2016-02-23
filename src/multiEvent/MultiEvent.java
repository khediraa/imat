package multiEvent;

import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * This class allows a control to have many eventhandlers at once.
 * Created by tuyenngo on 2016-02-22.
 */
public class MultiEvent<T extends Event> implements EventHandler<T> {
    Map<String, EventHandler<T>> events = new HashMap<>();

    public void addEvent(String name, EventHandler<T> eventHandler){
        events.put(name, eventHandler);
    }

    public void removeEvent(String name){
        events.remove(name);
    }

    @Override
    public void handle(T event) {
        for(String evtName : events.keySet()){
            events.get(evtName).handle(event);
        }
    }
}
