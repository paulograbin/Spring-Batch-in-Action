package com.example.batchprocessing.listeners;

import com.example.batchprocessing.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

public class PauloSkipListener implements SkipListener<Person, Person> {

    private static final Logger LOG = LoggerFactory.getLogger(PauloSkipListener.class);


    @Override
    public void onSkipInProcess(Person item, Throwable t) {
        LOG.info("Skipped item process {} because of {}", item, t.getMessage());
    }

    @Override
    public void onSkipInRead(Throwable t) {
        LOG.info("Skipped item read because of {}", t.getMessage());
    }

    @Override
    public void onSkipInWrite(Person item, Throwable t) {
        LOG.info("Skipped item writer {} because of {}", item, t.getMessage());
    }
}
