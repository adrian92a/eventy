package pl.net.rogala.eventy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.entity.User;
import pl.net.rogala.eventy.repository.EventRepository;
import pl.net.rogala.eventy.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private EventRepository eventRepository;
    private UserRepository userRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> showEventList() {
        return eventRepository.findAll(Sort.by("startDate"));
    }

    public Optional<Event> getSingleEvent(Long eventId) {
        return eventRepository.findById(eventId);
    }


//    public void getUserAsignToEvent() {
//        Event event = new Event();
//        event.getUser().getEmail();
//
//    }
//
////        Event event = eventRepository.findById(eventId)
////                .orElseThrow(() -> new RuntimeException("event not found"));
////
////        User user = userRepository.findByEmail(email)
////                .orElseThrow(() -> new RuntimeException("user not found"));
////
//        eventRepository.save(event);
//
//    }


}
