package pl.net.rogala.eventy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.net.rogala.eventy.service.EventService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiEventService {

    private EventService eventService;

    @Autowired
    public ApiEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public List<EventSummary> collectAllEvents() {
        List<EventSummary> eventSummaryList = eventService.showEventList().stream()
                .map(event -> new EventSummary(event.getId(), event.getName(), event.getStartDate(), event.getStopDate()))
                .collect(Collectors.toList());
        return eventSummaryList;
    }

    public List<EventSummary> collectEventsByDateRange(String startDate, String stopDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate stop = LocalDate.parse(stopDate, formatter);
        List<EventSummary> eventSummaryListWithDateRange = eventService.findEventsByDateRange(start, stop).stream()
                .map(event -> new EventSummary(event.getId(), event.getName(), event.getStartDate(), event.getStopDate()))
                .collect(Collectors.toList());
        return eventSummaryListWithDateRange;
    }
}
