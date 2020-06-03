package com.acme.mytrader.price.impl;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomPriceGenerator {

    public double generatePrice(double price){
        // get me random numbers 20 on either side of the price.
        final Random random = new Random();
        double rangeMax= price + 10;
        double rangeMin= price - 10;
        final Set<Double> set = new HashSet<>();
        for(int i=0; i<20;++i) {
            double rand = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
            set.add(rand);
        }
        return set.stream().limit(1).findFirst().get();
    }

}
