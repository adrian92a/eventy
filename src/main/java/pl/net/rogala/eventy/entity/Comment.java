package pl.net.rogala.eventy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name="event_id")
    private Long eventId;


}
