package task10.domain;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Comment {
    private String commentBody;
    private LocalDateTime date;
}
