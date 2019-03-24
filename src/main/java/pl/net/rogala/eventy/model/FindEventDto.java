package pl.net.rogala.eventy.model;

public class FindEventDto {
    private String name;
    private EventType eventType;

    public FindEventDto(String name, EventType eventType) {
        this.name = name;
        this.eventType = eventType;
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
}
