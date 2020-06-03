package com.acme.mytrader.strategy;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.impl.PriceListenerImpl;
import com.acme.mytrader.price.impl.PriceSourceImpl;
import com.acme.mytrader.price.impl.RandomPriceGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.concurrent.CompletableFuture;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TradingStrategyTest {
    @InjectMocks
    private TradingStrategy tradingStrategy;

    @Spy
    private PriceSourceImpl priceSource;

    @Mock
    private ExecutionService executionService;

    @Mock
    private PriceListenerImpl priceListener;

    @Mock
    private RandomPriceGenerator randomPriceGenerator;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        Mockito.doCallRealMethod().when(priceListener).setPriceSource(priceSource);
        Mockito.doCallRealMethod().when(priceListener).setExecutionService(executionService);
    }

    @Test
    public void testBuyIfThresholdIsReached() {
        double price = 115.0;
        String security = "IBM";
        double currentPrice=110;
        // Given
        Mockito.doCallRealMethod().when(priceSource).getPriceAndNotify(security,price);
        Mockito.doCallRealMethod().when(priceListener).priceUpdate(security,price);
        Mockito.when(randomPriceGenerator.generatePrice(price)).thenReturn(currentPrice);
        // When
        tradingStrategy.trade(security,price);
        // Then
        Mockito.verify(executionService,Mockito.times(1)).buy(security,currentPrice,100);
    }

    @Test
    public void testBuyIfThresholdIsNotReached() throws InterruptedException {
        double price = 115.0;
        String security = "IBM";
        double currentPrice=116;
        // Given
        Mockito.doCallRealMethod().when(priceSource).getPriceAndNotify(security,price);
        Mockito.doCallRealMethod().when(priceListener).priceUpdate(security,price);
        Mockito.when(randomPriceGenerator.generatePrice(price)).thenReturn(currentPrice);
        // When
        final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> tradingStrategy.trade(security, price));

        Thread.sleep(5000l);
        // Then
        Assert.assertThat(future.isDone(), is(equalTo(false)));
        Mockito.verify(executionService,Mockito.times(0)).buy(security,currentPrice,100);
    }
}
