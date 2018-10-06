package task16.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FibonacciPayload {
    private Integer fibonacci;
    private Integer previousSum;

    public FibonacciPayload(FibonacciPayload payload) {
        this.fibonacci = payload.getPreviousSum() + payload.getFibonacci();
        this.previousSum = payload.getFibonacci();
    }

    public FibonacciPayload() {
        fibonacci = 1;
        previousSum = 0;
    }

    public Integer getFibonacci() {
        return this.fibonacci;
    }
}
