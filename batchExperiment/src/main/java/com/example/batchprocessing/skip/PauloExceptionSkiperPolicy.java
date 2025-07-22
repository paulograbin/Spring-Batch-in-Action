package com.example.batchprocessing.skip;

import com.example.batchprocessing.exceptions.RandomSkipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class PauloExceptionSkiperPolicy implements SkipPolicy {

    private static final Logger LOG = LoggerFactory.getLogger(PauloExceptionSkiperPolicy.class);


    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        boolean assignableFrom = RandomSkipException.class.isAssignableFrom(t.getClass());

        if (assignableFrom) {
            LOG.warn("Will be skipped!!!!");
        }

        return assignableFrom;
    }
}
