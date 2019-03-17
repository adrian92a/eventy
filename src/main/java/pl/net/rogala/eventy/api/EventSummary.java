package pl.net.rogala.eventy.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EventSummary {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate stopDate;
}
