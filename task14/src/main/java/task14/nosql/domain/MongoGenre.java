package task14.nosql.domain;

import lombok.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class MongoGenre {
    private String genreName;
    private String genreDescription;
}
