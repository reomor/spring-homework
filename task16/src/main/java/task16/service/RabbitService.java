package task16.service;

import org.springframework.stereotype.Service;
import task16.domain.FibonacciPayload;
import task16.domain.Rabbit;

import java.util.ArrayList;
import java.util.List;

@Service
public class RabbitService {

    public List<Rabbit> hornyRabbits(FibonacciPayload payload) {
        List<Rabbit> rabbits = new ArrayList<>();
        for (int i = 0; i < payload.getFibonacciNumber(); i++) {
            rabbits.add(new Rabbit(i));
        }
        System.out.println(rabbits.size() + " rabbits are ready!");
        return rabbits;
    }
}
