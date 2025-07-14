package com.manning.sbia.ch01.batch;

import com.manning.sbia.ch01.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;


public class MyCustomerListener implements SkipListener<Product, Product> {

    private static final Logger LOG = LoggerFactory.getLogger(MyCustomerListener.class);


    @Override
    public void onSkipInRead(Throwable t) {
        LOG.info("a11111 {}", t);
    }


    @Override
    public void onSkipInWrite(Product item, Throwable t) {
        LOG.info("2222 {}", item);

    }

    @Override
    public void onSkipInProcess(Product item, Throwable t) {
        LOG.info("333 {}", item);

    }
}
