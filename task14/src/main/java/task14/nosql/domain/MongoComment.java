package task14.nosql.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MongoComment {
    private String commentBody;
    private LocalDateTime date;
}
