package pl.net.rogala.eventy.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByStartDateAfterAndStopDateBefore(LocalDate startDate, LocalDate stopDate);

    List<Event> findEventsByOwner(User user);
}
