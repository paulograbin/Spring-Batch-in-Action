package com.example.batchprocessing.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class PauloCustomChunkListener implements ChunkListener {

    private static final Logger LOG = LoggerFactory.getLogger(PauloCustomChunkListener.class);

    @Override
    public void beforeChunk(ChunkContext context) {
        LOG.info("before chunk");
    }

    @Override
    public void afterChunk(ChunkContext context) {
        LOG.info("after chunk");
    }
}
