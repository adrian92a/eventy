package pl.net.rogala.eventy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.net.rogala.eventy.entity.Comment;
import pl.net.rogala.eventy.entity.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByEvent_Id(Long id);

}
