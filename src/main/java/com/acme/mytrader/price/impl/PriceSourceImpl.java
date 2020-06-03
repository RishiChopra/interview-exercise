package com.acme.mytrader.price.impl;

import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Collections.unmodifiableList;
import static java.util.Optional.ofNullable;

/**
 * Simple implementation supporting a single security price movement track.
 */
public class PriceSourceImpl implements PriceSource {
    private List<PriceListener> priceListeners;
    private RandomPriceGenerator randomPriceGenerator;
    private double currentPrice = -1;

    public PriceSourceImpl(){
        priceListeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public void addPriceListener(final PriceListener listener) {
        priceListeners.add(listener);
    }

    @Override
    public void removePriceListener(final PriceListener listener) {
        priceListeners.remove(listener);
    }

    @Override
    public void getPriceAndNotify(final String security, final double price) {
        do{
            this.currentPrice = randomPriceGenerator.generatePrice(price);
            ofNullable(priceListeners).orElseGet(Collections::emptyList).
                    stream().forEach(p-> p.priceUpdate(security,price));
        }while(priceListeners.size() !=0);
    }

    public double getCurrentPrice(){
        return this.currentPrice;
    }

    @Override
    public void setRandomNumberGenerator(final RandomPriceGenerator randomPriceGenerator) {
        this.randomPriceGenerator = randomPriceGenerator;
    }

    public List<PriceListener> getPriceListeners() {
        return unmodifiableList(priceListeners);
    }
}
