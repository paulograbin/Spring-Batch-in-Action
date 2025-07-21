package com.example.batchprocessing.listeners;

import com.example.batchprocessing.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

public class PauloItemReaderListener implements ItemReadListener<Person> {

    private static final Logger LOG = LoggerFactory.getLogger(PauloItemReaderListener.class);


    @Override
    public void afterRead(Person item) {
        LOG.info("after item read {}", item);
    }

    @Override
    public void beforeRead() {
        LOG.info("before item read ");
    }
}
