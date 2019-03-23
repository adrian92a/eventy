package pl.net.rogala.eventy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.net.rogala.eventy.entity.AssignedToEvent;
import pl.net.rogala.eventy.entity.User;

import java.util.List;

@Repository
public interface AssignedToEventRepository extends JpaRepository<AssignedToEvent, Long> {
    List<User> findAllUsersAssignedToEventById(Long eventId);

}
