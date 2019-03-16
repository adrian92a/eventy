package pl.net.rogala.eventy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.repository.EventRepository;
import pl.net.rogala.eventy.service.EventService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class EventController {

    private EventService eventService;
    private EventRepository eventRepository;
    @Autowired
    public EventController(EventService eventService, EventRepository eventRepository) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/findEvents")
    public String showMainPage(@RequestParam("name") String name,
                               @RequestParam("option") String option,
                               Model model) {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        List<Event> eventList= eventService.showEventList();
        switch (option){
            case "przyszłe":eventList= eventRepository.findFutureEvents(name,localDate);
            break;
            case "trwające i przyszłe":eventList=eventRepository.findFutureAndActuallyEvents(name,localDate);
            break;
            case "wszystkie":eventList=eventRepository.findEvents(name,localDate);
        }

        model.addAttribute("eventList",eventList);
        return "home";  
    }

}
