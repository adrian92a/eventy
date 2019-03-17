package pl.net.rogala.eventy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.model.EventDto;
import pl.net.rogala.eventy.model.EventType;
import pl.net.rogala.eventy.model.FindEventDto;
import pl.net.rogala.eventy.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class EventController {

    private EventService eventService;
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/findEvents")
    public String showMainPage(
//            @RequestParam("name") String name,
//                               @RequestParam("selectValue") String option,
//                               @ModelAttribute("selectValue") FindEventDto.SelectValue selectValue,
            @ModelAttribute("findEventDto") FindEventDto findEventDto,
            Model model) {
        LocalDateTime localDate= LocalDateTime.now();

        model.addAttribute("eventTypes", EventType.values());

        List<EventDto> events = eventService.getEvents(findEventDto);
//
//        List<Event> eventList= eventService.showEventList();
//
//        if(findEventDto.getEventType().getLabel().equals("przyszłe")){
//            eventList= eventRepository.findByNameIsContainingAndStartDateEqualsOrStartDateIsAfterIgnoreCase("przyszłe",localDate,localDate);
//        }
//        if(findEventDto.getEventType().getLabel().equals("aktualne")){
//            eventList= eventRepository.findByNameIsContainingAndStartDateIsAfterIgnoreCase("aktualne",localDate);
//        }
//        if(findEventDto.getEventType().getLabel().equals("wszystkie")){
//            eventList= eventRepository.findByNameIsContainingIgnoreCase("wszystkie");
//        }
//        switch (option){
//
//            case "przyszłe":System.out.println(localDate);
//            eventList= eventRepository.findFutureEvents(name, localDate);
//            break;
//            case "trwające i przyszłe":eventList=eventRepository.findFutureAndActuallyEvents(name,localDate);
//            break;
//            case "wszystkie":eventList=eventRepository.findEvents(name,localDate);
//        }

        model.addAttribute("events", events);
        return "home";  
    }

}
