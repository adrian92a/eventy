package pl.net.rogala.eventy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiEventController {

    private EventService eventService;

    @Autowired
    public ApiEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventSummary>> showAllEvents(){

        List<EventSummary> eventSummaryList = eventService.getListOfFutureEvents().stream()
                .map(event->new EventSummary(event.getId(), event.getName(), event.getStartDate(), event.getStopDate()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventSummaryList);
    }
}
