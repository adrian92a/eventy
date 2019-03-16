package pl.net.rogala.eventy.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.net.rogala.eventy.entity.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("Select e from Event e where e.name like '%?1%' and startDate>?2")
    List<Event> findFutureEvents(String name, LocalDate date);

    @Query("Select e from Event e where e.name like '%?1%' and startDate>=?2")
    List<Event> findFutureAndActuallyEvents(String name, LocalDate date);

    @Query("Select e from Event e where e.name like '%?1%'")
    List<Event> findEvents(String name, LocalDate date);
}
