package pl.net.rogala.eventy.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.net.rogala.eventy.entity.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    public List<Event> findAllByStartDateAfterAndStopDateBefore(LocalDate startDate, LocalDate stopDate);


}
