package pl.net.rogala.eventy.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import pl.net.rogala.eventy.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Component
public class NewEventForm {

    @NotNull(message = "Nazwa wydarzenia jest obowiązkowa!")
    @NotBlank(message = "Nazwa wydarzenia jest obowiązkowa!")
    private String name;

    @Size(min=20, message = "Opis wydarzenia jest obowiązkowy!")
    private String description;

    @NotNull(message = "Wydarzenie musi mieć poczatkowy termin!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate stopDate;

    private User owner;
}
