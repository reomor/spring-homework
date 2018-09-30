package task15.nosql.domain;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class MongoComment {
    private String commentBody;
    private LocalDateTime date;
}
