package nl.hsleiden.iprwc.s1132776.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomService {

    public static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return String.format("%06d", number);
    }
}
