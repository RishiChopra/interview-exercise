package com.acme.mytrader.price;

import com.acme.mytrader.price.impl.PriceListenerImpl;
import com.acme.mytrader.price.impl.PriceSourceImpl;
import com.acme.mytrader.price.impl.RandomPriceGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class PriceSourceImplTest {

    private PriceSourceImpl priceSource = new PriceSourceImpl();

    @Mock
    private RandomPriceGenerator randomPriceGenerator;

    @Mock
    private PriceListenerImpl priceListener;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        priceSource.setRandomNumberGenerator(randomPriceGenerator);
    }

    @Test
    public void testAddPriceListener(){
        priceSource.addPriceListener(priceListener);
        Assert.assertTrue( priceSource.getPriceListeners().size() ==1);
    }

    @Test
    public void testRemovePriceListener(){
        priceSource.addPriceListener(priceListener);
        priceSource.removePriceListener(priceListener);
        Assert.assertTrue( priceSource.getPriceListeners().size() ==0);
    }
}
