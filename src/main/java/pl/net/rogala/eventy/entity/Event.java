package pl.net.rogala.eventy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1000)
    private String decription;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate stopDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;


}
