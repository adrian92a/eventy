package pl.net.rogala.eventy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.net.rogala.eventy.entity.AssignedToEvent;

@Repository
public interface AssignedToEventRepository extends JpaRepository<AssignedToEvent, Long> {

}
