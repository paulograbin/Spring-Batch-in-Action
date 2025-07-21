package com.example.batchprocessing.listeners;

import com.example.batchprocessing.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

public class PauloItemReaderListener implements ItemReadListener<Person> {

    private static final Logger LOG = LoggerFactory.getLogger(PauloCustomChunkListener.class);


    @Override
    public void afterRead(Person item) {
        LOG.info("afterRead {}", item);
    }

    @Override
    public void beforeRead() {
        LOG.info("beforeRead");
    }
}
