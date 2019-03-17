package pl.net.rogala.eventy.model;

import java.time.LocalDateTime;

public class EventDto {
    private Long id;
    private String name;
    private String decription;
    private LocalDateTime startDate;
    private LocalDateTime stopDate;

    public EventDto(Long id, String name, String decription, LocalDateTime startDate, LocalDateTime stopDate) {
        this.id = id;
        this.name = name;
        this.decription = decription;
        this.startDate = startDate;
        this.stopDate = stopDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDecription() {
        return decription;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getStopDate() {
        return stopDate;
    }
}
