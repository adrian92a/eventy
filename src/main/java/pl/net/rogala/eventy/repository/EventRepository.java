package pl.net.rogala.eventy.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.net.rogala.eventy.entity.Event;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {


    public List<Event> findAllByStartDateAfterAndStopDateBefore(LocalDate startDate, LocalDate stopDate);

  List<Event> findAll(Predicate predicate);
  Optional<Event> findById(Long id);


  List<Event> findByNameIsContainingAndStartDateEqualsOrStartDateIsAfterIgnoreCase(String name,LocalDateTime date1,LocalDateTime date2);
  List<Event> findByNameIsContainingAndStartDateIsAfterIgnoreCase(String name,LocalDateTime date);
  List<Event> findByNameIsContainingIgnoreCase(String name);


  @Query("Select e from Event e where e.name like '%?1%' and e.startDate>?2")
  List<Event> findFutureEvents(String name, LocalDateTime date);

  @Query("Select e from Event e where e.name like '%?1%' and e.startDate>=?2")
  List<Event> findFutureAndActuallyEvents(String name, LocalDateTime date);

  @Query("Select e from Event e where e.name like '%?1%'")
  List<Event> findEvents(String name, LocalDateTime date);

}
