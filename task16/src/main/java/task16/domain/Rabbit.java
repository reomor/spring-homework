package task16.domain;

import lombok.ToString;

@ToString
public class Rabbit {

    private int id;
    private int correlationId;

    public Rabbit(int id) {
        this.id = id;
        this.correlationId = id / 10;
    }

    public int getCorrelationId() {
        return correlationId;
    }
}
