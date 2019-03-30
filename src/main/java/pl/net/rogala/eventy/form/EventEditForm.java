package pl.net.rogala.eventy.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class EventEditForm {

    @NotBlank(message = "Nazwa wydarzenia jest obowiązkowa!")
    private String name;

    @Size(min=20,max = 1000, message = "Opis wydarzenia jest obowiązkowy! Powinien mieć od {min} do {max} znaków!")
    private String description;

    @NotNull(message = "Wydarzenie musi mieć poczatkowy termin!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate stopDate;


}
