package pl.net.rogala.eventy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.net.rogala.eventy.entity.AssignedToEvent;
import pl.net.rogala.eventy.entity.User;

import java.util.List;

@Repository
public interface AssignedToEventRepository extends JpaRepository<AssignedToEvent, Long> {
    List<AssignedToEvent> findAllByEvent_Id(Long eventId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from assigned_to_event a where a.event_id=:eventId and a.user_id = :userId")
    void removeRecord(@Param("userId") Long userId, @Param("eventId") Long eventId);


}
