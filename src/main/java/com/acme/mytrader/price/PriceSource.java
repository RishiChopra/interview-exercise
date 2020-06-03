package com.acme.mytrader.price;

import com.acme.mytrader.price.impl.RandomPriceGenerator;

public interface PriceSource {
    void addPriceListener(PriceListener listener);
    void removePriceListener(PriceListener listener);
    void getPriceAndNotify(String security, double price);
    double getCurrentPrice();

    void setRandomNumberGenerator(RandomPriceGenerator randomPriceGenerator);
}
