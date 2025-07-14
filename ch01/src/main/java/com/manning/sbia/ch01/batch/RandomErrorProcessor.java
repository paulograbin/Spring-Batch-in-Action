package com.manning.sbia.ch01.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Random;

public class RandomErrorProcessor implements ItemProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(RandomErrorProcessor.class);


    @Override
    public Object process(Object item) throws Exception {
        LOG.info("Processing item: {}", item);

        int i = new Random().nextInt(10);

        if (i >= 9) {
            LOG.error("Random error at " + item);
            throw new RuntimeException("Random exception at " + item);
        }

        return item;
    }
}
