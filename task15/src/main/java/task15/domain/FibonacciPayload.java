package task15.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

//@Getter
@ToString
@AllArgsConstructor
public class FibonacciPayload {

    private Integer fibonacci;
    private Integer previousSum;
    private int limit;
    private boolean limitIsReached;

    public FibonacciPayload(FibonacciPayload payload) {
        if (payload.limit - 1 == 0) {
            this.limit = 0;
            this.limitIsReached = true;
        } else {
            this.limit = payload.limit - 1;
            this.limitIsReached = false;
        }
        this.fibonacci = payload.previousSum + payload.fibonacci;
        this.previousSum = payload.fibonacci;
    }

    public FibonacciPayload(int limit) {
        this.fibonacci = 1;
        this.previousSum = 0;
        this.limit = limit;
        this.limitIsReached = false;
    }

    public boolean isLimitIsReached() {
        return limitIsReached;
    }

    public Integer getFibonacciNumber() {
        return this.previousSum;
    }
}
