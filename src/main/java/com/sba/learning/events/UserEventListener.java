package com.sba.learning.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

    @EventListener
    public void handleUserCreated(UserCreatedEvent event) {
        System.out.println("User created with ID: " + event.getUserId());
        // Add any additional async logic here
    }
}