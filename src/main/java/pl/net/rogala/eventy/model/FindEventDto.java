package pl.net.rogala.eventy.model;

import pl.net.rogala.eventy.entity.User;

import java.util.List;

public class FindEventDto {
    private String name;
    private EventType eventType;
    private User owner;
    private String ownerName;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public FindEventDto(String name, EventType eventType, String ownerName) {
        this.name = name;
        this.eventType = eventType;
        this.ownerName = ownerName;
    }

    public FindEventDto(String name, EventType eventType) {
        this.name = name;
        this.eventType = eventType;
    }

    public FindEventDto(String name, EventType eventType, User owner) {
        this.name = name;
        this.eventType = eventType;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public FindEventDto() {
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public FindEventDto(EventType eventType) {
        this.eventType = eventType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventType getEventType() {
        return eventType;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
