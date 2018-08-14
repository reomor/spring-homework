package task10.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Comment {
    private String body;
    private LocalDateTime date;
}
