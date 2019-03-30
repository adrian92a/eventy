package pl.net.rogala.eventy.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.net.rogala.eventy.entity.User;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentEditForm {

    private User commentator;

    @Column(nullable = false)
    private LocalDateTime added;

    @Column(nullable = false, length = 1500)
    private String body;

    @Column(name="event_id")
    private Long eventId;

}
