package pl.net.rogala.eventy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.net.rogala.eventy.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiEventController {
    private EventService eventService;

    @Autowired
    public ApiEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventSummary>> showAllEvents() {
        return ResponseEntity.ok(eventService.collectAllEvents());
    }

    @GetMapping("/events/{startDate},{stopDate}")
    public ResponseEntity<List<EventSummary>> showEventsByDateRange(@PathVariable String startDate, @PathVariable String stopDate) {
        return ResponseEntity.ok(eventService.collectEventsByDateRange(startDate, stopDate));
    }
}
