package pl.net.rogala.eventy.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.entity.QEvent;
import pl.net.rogala.eventy.model.EventDto;
import pl.net.rogala.eventy.model.EventType;
import pl.net.rogala.eventy.model.FindEventDto;
import pl.net.rogala.eventy.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private EventRepository eventRepository;

    private final QEvent event = QEvent.event;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> showEventList() {
        return eventRepository.findAll(Sort.by("startDate"));
    }

    public List<EventDto> getEvents(FindEventDto findEventDto) {

        System.out.println("Name: " + findEventDto.getName() + "     Event Type:" + findEventDto.getEventType());
        BooleanExpression booleanExpression = Expressions.asBoolean(true).isTrue();
        if (findEventDto.getName() != null) {
            booleanExpression = booleanExpression.and(event.name.contains(findEventDto.getName()));
        }
        if (findEventDto.getEventType().equals(EventType.CURRENT)) {

            booleanExpression = booleanExpression.and(event.startDate.after(LocalDateTime.now()).or(event.startDate.eq(LocalDateTime.now())));
        }
        if (findEventDto.getEventType().equals(EventType.FUTURE)) {
            booleanExpression = booleanExpression.and(event.startDate.eq(LocalDateTime.now()));
        }

        List<Event> all = eventRepository.findAll(booleanExpression);
        return all.stream().map(Event::toEventDto).collect(Collectors.toList());
    }
}
