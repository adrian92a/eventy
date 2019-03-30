package pl.net.rogala.eventy.entity;

import lombok.Getter;
import lombok.Setter;
import pl.net.rogala.eventy.model.EventDto;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable=false, length = 1000)
    private String decription;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate stopDate;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User owner;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    List<Comment> comments;

    public EventDto toEventDto() {
        return new EventDto(
                this.id,
                this.name,
                this.decription,
                this.startDate,
                this.stopDate,
        this.comments.stream()
                .map(Comment::toDto)
                .collect(Collectors.toList()));
            }

}
