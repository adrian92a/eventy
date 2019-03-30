package pl.net.rogala.eventy.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class EventDto {
    private Long id;
    private String name;
    private String decription;
    private LocalDate startDate;
    private LocalDate stopDate;
    private List<CommentDto> comments;

    public EventDto(Long id, String name, String description, LocalDate startDate, LocalDate stopDate, List<CommentDto> comments) {
        this.id = id;
        this.name = name;
        this.decription = description;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.comments = comments;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getStopDate() {
        return stopDate;
    }
}
