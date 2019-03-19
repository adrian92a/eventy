package pl.net.rogala.eventy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiEventController {
    private ApiEventService apiEventService;

    @Autowired
    public ApiEventController(ApiEventService apiEventService) {
        this.apiEventService = apiEventService;
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventSummary>> showAllEvents(){
        return ResponseEntity.ok(apiEventService.collectAllEvents());
    }

    @GetMapping("/events/{startDate},{stopDate}")
    public ResponseEntity<List<EventSummary>> showEventsByDateRange(@PathVariable String startDate, @PathVariable String stopDate){
        return ResponseEntity.ok(apiEventService.collectEventsByDateRange(startDate, stopDate));
    }
}
