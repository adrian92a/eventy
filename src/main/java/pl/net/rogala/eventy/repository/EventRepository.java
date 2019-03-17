package pl.net.rogala.eventy.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.net.rogala.eventy.entity.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

//    @Modifying
//    @Query("select e from event e where start_date >= :date")
//    List<Event> findFutureEvents(@Param("date") LocalDate localDate);

}
