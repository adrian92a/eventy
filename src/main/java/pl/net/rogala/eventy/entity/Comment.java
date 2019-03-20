package pl.net.rogala.eventy.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.convert.Jsr310Converters;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User commentator;

    @Column(nullable = false)
    private LocalDateTime added;

    @Column(nullable = false, length = 1500)
    private String body;

    @ManyToOne
    @JoinColumn(name="event_id")
    private Event event;

}
