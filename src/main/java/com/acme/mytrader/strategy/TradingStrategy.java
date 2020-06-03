package com.acme.mytrader.strategy;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;
import com.acme.mytrader.price.impl.RandomPriceGenerator;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
public class TradingStrategy {
    private PriceSource priceSource;
    private PriceListener priceListener;
    private ExecutionService executionService;
    private RandomPriceGenerator randomPriceGenerator;

    public TradingStrategy(final PriceSource priceSource, final RandomPriceGenerator randomPriceGenerator, final PriceListener priceListener,final ExecutionService executionService) {
        this.priceSource = priceSource;
        this.randomPriceGenerator = randomPriceGenerator;
        this.priceListener = priceListener;
        this.executionService = executionService;
    }

    /**
     * trade the security
     * @param security
     * @param price
     */
    public void trade(final String security, final double price) {
        priceSource.setRandomNumberGenerator(randomPriceGenerator);
        priceListener.setPriceSource(priceSource);
        priceListener.setExecutionService(executionService);
        priceSource.getPriceAndNotify(security,price);
    }

    public void setPriceSource(final PriceSource priceSource) {
        this.priceSource = priceSource;
    }

    public void setPriceListener(final PriceListener priceListener) {
        this.priceListener = priceListener;
    }

    public void setExecutionService(final ExecutionService executionService) {
        this.executionService = executionService;
    }
}
