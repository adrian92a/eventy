package pl.net.rogala.eventy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime startDate;

    private LocalDateTime stopDate;

    @ManyToOne
    @JoinColumn (name="user_id")
    private User owner;



}
