package com.manning.sbia.ch01.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.LineCallbackHandler;

public class SkippedLineLogger implements LineCallbackHandler {

    private static final Logger LOG = LoggerFactory.getLogger(PirocoTasklet.class);

    @Override
    public void handleLine(String line) {
        LOG.info("Line skpped: {}", line);
    }
}
