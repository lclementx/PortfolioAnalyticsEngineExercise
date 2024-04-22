package org.util;

import org.service.Event;
import org.service.ISubscriber;

public class PrintSubscriber implements ISubscriber {
    @Override
    public void update(Event event) {
        System.out.println("Received event: " + event.getEvent());
    }
}
