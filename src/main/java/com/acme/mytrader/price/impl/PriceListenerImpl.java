package com.acme.mytrader.price.impl;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;

public class PriceListenerImpl implements PriceListener {
    private PriceSource priceSource;
    private ExecutionService executionService;

    @Override
    public void setPriceSource(final PriceSource priceSource) {
        this.priceSource = priceSource;
        priceSource.addPriceListener(this);
    }

    public void setExecutionService(final ExecutionService executionService) {
        this.executionService = executionService;
    }

    @Override
    public void priceUpdate(final String security, final double price) {
        final double currentPrice = priceSource.getCurrentPrice();
        if (currentPrice > 0.0 && currentPrice < price) {
            executionService.buy(security, currentPrice, 100);
            priceSource.removePriceListener(this);
        }
    }
}
