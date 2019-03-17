package pl.net.rogala.eventy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.form.EventEditForm;
import pl.net.rogala.eventy.repository.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> showEventList(){
        return eventRepository.findAll(Sort.by("startDate"));
    }

    public Optional<Event> getSingleEvent(Long eventId) {
        return eventRepository.findById(eventId);
    }

    public void editEvent(Long eventId, EventEditForm eventEditForm) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("event not found"));

        event.setName(eventEditForm.getName());
        event.setDecription(eventEditForm.getDescription());
        event.setStartDate(eventEditForm.getStartDate());
        event.setStopDate(eventEditForm.getStopDate());

        eventRepository.save(event);
    }
}
