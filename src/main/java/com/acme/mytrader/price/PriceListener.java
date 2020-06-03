package com.acme.mytrader.price;

import com.acme.mytrader.execution.ExecutionService;

public interface PriceListener {
    void priceUpdate(String security, double price);

    void setExecutionService(final ExecutionService executionService);
    void setPriceSource(PriceSource priceSource);
}
